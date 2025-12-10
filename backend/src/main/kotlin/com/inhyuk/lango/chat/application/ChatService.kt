package com.inhyuk.lango.chat.application

import com.fasterxml.jackson.databind.ObjectMapper
import com.inhyuk.lango.chat.domain.ChatMessage
import com.inhyuk.lango.chat.domain.ChatSession
import com.inhyuk.lango.chat.domain.MessageSender
import com.inhyuk.lango.chat.dto.ChatSessionResponse
import com.inhyuk.lango.chat.dto.ScenarioGenerationResponse
import com.inhyuk.lango.chat.infrastructure.ChatMessageRepository
import com.inhyuk.lango.chat.infrastructure.ChatSessionRepository
import com.inhyuk.lango.llm.prompt.PromptManager
import com.inhyuk.lango.user.infrastructure.UserRepository
import dev.langchain4j.data.message.AiMessage
import dev.langchain4j.data.message.SystemMessage
import dev.langchain4j.data.message.UserMessage
import dev.langchain4j.model.output.Response
import dev.langchain4j.model.StreamingResponseHandler
import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.chat.StreamingChatModel
import dev.langchain4j.model.chat.request.ChatRequest
import dev.langchain4j.model.chat.request.ResponseFormat
import dev.langchain4j.model.chat.request.ResponseFormatType
import dev.langchain4j.model.chat.request.json.JsonObjectSchema
import dev.langchain4j.model.chat.request.json.JsonSchema
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Service
class ChatService(
    private val userRepository: UserRepository,
    private val chatSessionRepository: ChatSessionRepository,
    private val chatMessageRepository: ChatMessageRepository,
    private val promptManager: PromptManager,
    private val chatModel: ChatModel,
    private val streamingChatModel: StreamingChatModel,
) {

    @Transactional
    fun createSession(id: String, topic: String?): ChatSessionResponse {
        val user = userRepository.findById(id).orElseThrow { IllegalArgumentException("User not found") }
        
        val userLevel = user.currentLevel ?: "Beginner"
        val prompt = promptManager.getScenarioGenerationPrompt(userLevel, topic)
        val responseFormat = ResponseFormat.builder()
            .type(ResponseFormatType.JSON)
            .jsonSchema(JsonSchema.builder()
                .name("ScenarioGenerationResponse")
                .rootElement(JsonObjectSchema.builder()
                    .description("Scenario generation response")
                    .addStringProperty("scenario","시나리오에 대한 설명...")
                    .addStringProperty("yourRole","당신의 역할(예: Barista, Ticket Agent, Team Leader)")
                    .addStringProperty("userRole","사용자의 역할(예: Customer, Waiter, Team Member)")
                    .required("scenario", "yourRole", "userRole")
                    .build())
                .build())
            .build()

        val request = ChatRequest.builder()
            .messages(UserMessage(prompt))
            .responseFormat(responseFormat)
            .build()
        val response = chatModel.chat(request)
        val scenarioData = ObjectMapper().readValue(response.aiMessage().text(), ScenarioGenerationResponse::class.java)

        val session = chatSessionRepository.save(ChatSession(
            user = user,
            scenario = scenarioData.scenario,
            userRole = scenarioData.userRole,
            aiRole = scenarioData.yourRole,
            userLevel = userLevel
        ))
        

        return ChatSessionResponse.from(session)
    }

    @Transactional
    fun startSessionChat(sessionId: Long): ChatSessionResponse {
        val session = chatSessionRepository.findById(sessionId)
            .orElseThrow { IllegalArgumentException("Session not found") }

        val prompt = promptManager.getChatSystemPrompt(
            aiRole = session.aiRole,
            userRole = session.userRole,
            scenario = session.scenario,
            level = session.userLevel
        )
        val request = ChatRequest.builder()
            .messages(SystemMessage(prompt))
            .build()

        return ChatSessionResponse.from(session)
    }

    @Transactional
    fun chatMessage(sessionId: Long, userMessageContent: String) {
        val session = chatSessionRepository.findById(sessionId)
            .orElseThrow { IllegalArgumentException("Session not found") }
            
        // Save User Message
        saveMessage(session, userMessageContent, MessageSender.USER)
        
        // Build Context
        val history = chatMessageRepository.findBySessionIdOrderByCreatedAtAsc(sessionId)
        val lcMessages = mutableListOf<dev.langchain4j.data.message.ChatMessage>()
        
        // Add System Prompt
        lcMessages.add(SystemMessage(promptManager.getChatSystemPrompt(session.aiRole, session.userLevel, session.scenario)))
        
        // Add History
        history.forEach { msg ->
            when (msg.sender) {
                MessageSender.USER -> lcMessages.add(UserMessage(msg.content))
                MessageSender.AI -> lcMessages.add(AiMessage(msg.content))
                else -> {} // Skip System internal messages if any
            }
        }
        
        // Call Streaming Model
        val sb = StringBuilder()
        streamingChatModel.generate(lcMessages, object : StreamingResponseHandler<AiMessage> {
            override fun onNext(token: String) {
                try {
                    emitter.send(token)
                    sb.append(token)
                } catch (e: Exception) {
                    emitter.completeWithError(e)
                }
            }

            override fun onComplete(response: Response<AiMessage>) {
                try {
                    // Save AI Response
                     // Note: Internal logic of saveMessage needs to be handled carefully in async/transactional context
                     // Ideally, we should delegate this to a transactional method or repository directly.
                     // For simplicity in MVP, we might call repository directly here or use a self-injected service method.
                     // But strictly speaking, transaction boundaries might be tricky inside callback.
                     // However, JPA save is usually fine if session is active or we open a new one.
                     // Given this is a callback, it runs on a different thread usually.
                     // We will modify saveMessage to NOT rely on class-level transaction for the callback part, 
                     // or assume specific thread context.
                    // Actually, let's just use the repository here. The repository itself is thread-safe.
                    createAndSaveMessage(session, sb.toString(), MessageSender.AI)
                    emitter.complete()
                } catch (e: Exception) {
                    emitter.completeWithError(e)
                }
            }

            override fun onError(error: Throwable) {
                emitter.completeWithError(error)
            }
        })
    }
    
    // Extracted helper
    private fun saveMessage(session: ChatSession, content: String, sender: MessageSender) {
       chatMessageRepository.save(ChatMessage(session = session, content = content, sender = sender))
    }
    
    // Separate method for callback usage (avoiding @Transactional proxy issues if private, 
    // but repository.save is transactional by default so it's fine)
    fun createAndSaveMessage(session: ChatSession, content: String, sender: MessageSender) {
        chatMessageRepository.save(ChatMessage(session = session, content = content, sender = sender))
    }
}
