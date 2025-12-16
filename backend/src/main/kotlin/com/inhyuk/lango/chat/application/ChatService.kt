package com.inhyuk.lango.chat.application

import com.inhyuk.lango.chat.domain.ChatMessageEntity
import com.inhyuk.lango.chat.domain.ChatSessionEntity
import com.inhyuk.lango.chat.domain.MessageSender
import com.inhyuk.lango.chat.domain.ScenarioChatMessage
import com.inhyuk.lango.chat.dto.ChatMessageResponse
import com.inhyuk.lango.chat.dto.ChatSessionResponse
import com.inhyuk.lango.chat.dto.ScenarioGenerationResponse
import com.inhyuk.lango.chat.infrastructure.ChatMessageRepository
import com.inhyuk.lango.chat.infrastructure.ChatSessionRepository
import com.inhyuk.lango.chat.prompt.ChatPrompt
import com.inhyuk.lango.level.domain.Levels
import com.inhyuk.lango.level.infrastructure.UserLevelRepository
import com.inhyuk.lango.llm.prompt.PromptManager
import com.inhyuk.lango.user.infrastructure.UserRepository
import dev.langchain4j.data.message.AiMessage
import dev.langchain4j.data.message.ChatMessage
import dev.langchain4j.data.message.SystemMessage
import dev.langchain4j.data.message.UserMessage
import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.chat.request.ChatRequest
import dev.langchain4j.model.chat.request.ResponseFormat
import dev.langchain4j.model.chat.request.ResponseFormatType
import dev.langchain4j.model.chat.request.json.JsonObjectSchema
import dev.langchain4j.model.chat.request.json.JsonSchema
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tools.jackson.databind.ObjectMapper

@Service
class ChatService(
    private val userRepository: UserRepository,
    private val chatSessionRepository: ChatSessionRepository,
    private val chatMessageRepository: ChatMessageRepository,
    private val chatModel: ChatModel,
    private val objectMapper: ObjectMapper,
    private val userLevelRepository: UserLevelRepository,
) {

    fun getChatSessions(userId: String): List<ChatSessionEntity> {
        return chatSessionRepository.findByUserIdOrderByCreatedAtDesc(userId)
    }

    fun getChatSession(sessionId: String): ChatSessionEntity {
        return chatSessionRepository.findById(sessionId).orElseThrow { IllegalArgumentException("Session not found") }
    }

    fun getChatHistory(sessionId: String): List<ChatMessageEntity> {
        return chatMessageRepository.findBySessionIdOrderByCreatedAtAsc(sessionId)
    }

    @Transactional
    fun createSession(userId: String, topic: String?): ChatSessionEntity? {
        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException("User not found") }
        
        val userLevel = userLevelRepository.findByUserId(userId) ?: throw IllegalArgumentException("User level not found")
        val level = Levels.findLevel(userLevel.level) ?: throw IllegalArgumentException("Invalid user level")

        val prompt = ChatPrompt.getScenarioGenerationPrompt(level, topic)
        val responseFormat = ResponseFormat.builder()
            .type(ResponseFormatType.JSON)
            .jsonSchema(JsonSchema.builder()
                .name("ScenarioGenerationResponse")
                .rootElement(JsonObjectSchema.builder()
                    .description("Scenario generation response")
                    .addStringProperty("scenario","시나리오에 대한 설명...")
                    .addStringProperty("title","시나리오 제목")
                    .addStringProperty("aiRole","당신의 역할(예: 바리스타, 매표소 직원, 팀장)")
                    .addStringProperty("userRole","사용자의 역할(예: 고객, 웨이터, 팀원)")
                    .required("scenario", "aiRole", "userRole", "title")
                    .build())
                .build())
            .build()

        val request = ChatRequest.builder()
            .messages(UserMessage(prompt))
            .temperature(0.8)
            .responseFormat(responseFormat)
            .build()
        val response = chatModel.chat(request)
        val scenarioData = objectMapper.readValue(response.aiMessage().text(), ScenarioGenerationResponse::class.java)

        val session = user.id?.let {
            chatSessionRepository.save(ChatSessionEntity(
                userId = it,
                scenario = scenarioData.scenario,
                userRole = scenarioData.userRole,
                aiRole = scenarioData.aiRole,
                userLevel = userLevel.level,
                title = scenarioData.title
            ))
        }
        

        return session
    }

    @Transactional
    fun startSessionChat(sessionId: String): ScenarioChatMessage {
        val session = chatSessionRepository.findById(sessionId)
            .orElseThrow { IllegalArgumentException("Session not found") }
        val level = Levels.findLevel(session.userLevel) ?: throw IllegalArgumentException("Invalid user level")

        val prompt = ChatPrompt.getChatSystemPrompt(
            aiRole = session.aiRole,
            userRole = session.userRole,
            level = level,
            scenario = session.scenario
        )
        val responseFormat = ResponseFormat.builder()
            .type(ResponseFormatType.JSON)
            .jsonSchema(JsonSchema.builder()
                .name("ScenarioChatResponse")
                .rootElement(JsonObjectSchema.builder()
                    .description("Scenario chat response")
                    .addStringProperty("message","롤플레이 응답문 (영문)")
                    .addStringProperty("translate","응답문의 번역 (한글)")
                    .addStringProperty("code","응답에 대한 구분값 입니다. 사용자가 시나리오와 안맞는 대화를 하거나 부적절한 대화나 요청을 하면 '-1', 정상적인 대화인 경우 '0'을 입력")
                    .required("content", "translate", "code")
                    .build())
                .build())
            .build()
        val request = ChatRequest.builder()
            .messages(SystemMessage(prompt))
            .temperature(0.8)
            .responseFormat(responseFormat)
            .build()

        val response = chatModel.chat(request)
        var scenarioData = objectMapper.readValue(response.aiMessage().text(), ScenarioChatMessage::class.java)
        if(scenarioData.code != 0) {
            scenarioData = scenarioData.copy(message = "Sorry, I didn't understand you.")
        }
        saveAiMessage(session, scenarioData)

        return scenarioData
    }

    @Transactional
    fun chatMessage(userId : String, sessionId: String, userMessageContent: String) : ScenarioChatMessage{
        val session = chatSessionRepository.findById(sessionId)
            .orElseThrow { IllegalArgumentException("Session not found") }
        val level = Levels.findLevel(session.userLevel) ?: throw IllegalArgumentException("Invalid user level")
        // Save User Message
        saveUserMessage(session, userMessageContent)
        
        // Build Context
        val history = chatMessageRepository.findBySessionIdOrderByCreatedAtAsc(sessionId)
        val lcMessages = mutableListOf<ChatMessage>()
        
        history.map { it->
            when(it.sender) {
                MessageSender.USER -> UserMessage(it.content)
                MessageSender.AI -> AiMessage(it.content)
                else -> {}
            }
        }

        val prompt = ChatPrompt.getChatSystemPrompt(
            aiRole = session.aiRole,
            userRole = session.userRole,
            level = level,
            scenario = session.scenario
        )
        val responseFormat = ResponseFormat.builder()
            .type(ResponseFormatType.JSON)
            .jsonSchema(JsonSchema.builder()
                .name("ScenarioChatResponse")
                .rootElement(JsonObjectSchema.builder()
                    .description("Scenario chat response")
                    .addStringProperty("message","롤플레이 응답문 (영문)")
                    .addStringProperty("translate","응답문의 번역 (한글)")
                    .addStringProperty("code","응답에 대한 구분값 입니다. 사용자가 시나리오와 안맞는 대화를 하거나 부적절한 대화나 요청을 하면 '-1', 정상적인 대화인 경우 '0'을 입력")
                    .required("content", "translate", "code")
                    .build())
                .build())
            .build()
        val request = ChatRequest.builder()
            .messages(SystemMessage(prompt),*lcMessages.toTypedArray())
            .temperature(0.8)
            .responseFormat(responseFormat)
            .build()

        val response = chatModel.chat(request)
        var scenarioData = objectMapper.readValue(response.aiMessage().text(), ScenarioChatMessage::class.java)
        if(scenarioData.code != 0) {
            scenarioData = scenarioData.copy(message = "Sorry, I didn't understand you.")
        }
        saveAiMessage(session, scenarioData)

        return scenarioData
    }
    
    private fun saveAiMessage(session: ChatSessionEntity, message : ScenarioChatMessage) {
        session.id?.let {
            chatMessageRepository.save(ChatMessageEntity(
                sessionId = it,
                content = message.message,
                subContent = message.translate,
                sender = MessageSender.AI,
            ))
        }
    }
    
    fun saveUserMessage(session: ChatSessionEntity, content: String) {
        session.id?.let {
            chatMessageRepository.save(ChatMessageEntity(
                sessionId = it,
                content = content,
                sender = MessageSender.USER
            ))
        }
    }
}
