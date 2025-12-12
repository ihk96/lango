package com.inhyuk.lango.level.application

import com.inhyuk.lango.level.dto.GrammarTestQuestion
import com.inhyuk.lango.level.dto.ReadingTestQuestion
import com.inhyuk.lango.level.dto.VocabularyTestQuestion
import com.inhyuk.lango.level.prompt.CEFRLevelPrompt
import com.inhyuk.lango.level.prompt.LevelTestGuidePrompt
import dev.langchain4j.data.message.UserMessage
import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.chat.request.ChatRequest
import dev.langchain4j.model.chat.request.ResponseFormat
import dev.langchain4j.model.chat.request.ResponseFormatType
import dev.langchain4j.model.chat.request.json.JsonArraySchema
import dev.langchain4j.model.chat.request.json.JsonObjectSchema
import dev.langchain4j.model.chat.request.json.JsonSchema
import dev.langchain4j.model.chat.request.json.JsonStringSchema
import org.springframework.stereotype.Component
import tools.jackson.core.type.TypeReference
import tools.jackson.databind.ObjectMapper

@Component
class LevelTestFactory(
    private val chatModel: ChatModel,
    private val objectMapper: ObjectMapper
) {

    fun generateVocaTests(): List<VocabularyTestQuestion> {

        val prompt = """
            학습자의 정확한 CEFR 레벨(A1.1~C2.3)을 파악하기 위한 어휘 문제를 생성해야 합니다.
            CEFR 레벨에 대한 설명은 아래 Level Description을 참고하세요.
            <Level Description>
                ${CEFRLevelPrompt.Description}
            </Level Description>
            ${LevelTestGuidePrompt.getVocaTestSystemPrompt()}
        """.trimIndent()

        val responseFormat = ResponseFormat.builder()
            .type(ResponseFormatType.JSON)
            .jsonSchema(JsonSchema.builder()
                .name("response")
                .rootElement(JsonArraySchema.builder()
                    .description("생성한 문제 리스트")
                    .items(JsonObjectSchema.builder()
                        .addStringProperty("question","지문")
                        .addStringProperty("answer","정답")
                        .addStringProperty("level","해당하는 CEFR 레벨")
                        .addStringProperty("type","해당하는 문제 유형. 빈칸 채우기의 경우 \"fill\", 단어 뜻 맞추기의 경우 \"meaning\"")
                        .required("question", "answer", "level", "type")
                        .build())
                    .build())
                .build())
            .build()

        val request = ChatRequest.builder()
            .messages(UserMessage(prompt))
            .temperature(0.8)
            .responseFormat(responseFormat)
            .build()

        val response = chatModel.chat(request)
        val questions = objectMapper.readValue(response.aiMessage().text(), object : TypeReference<List<VocabularyTestQuestion>>() {})

        return questions
    }

    fun generateGrammarTests(): List<GrammarTestQuestion> {

        val prompt = """
            학습자의 정확한 CEFR 레벨(A1.1~C2.3)을 파악하기 위한 문법 문제를 생성해야 합니다.
            CEFR 레벨에 대한 설명은 아래 Level Description을 참고하세요.
            <Level Description>
                ${CEFRLevelPrompt.Description}
            </Level Description>
            ${LevelTestGuidePrompt.getGrammarTestSystemPrompt()}
        """.trimIndent()

        val responseFormat = ResponseFormat.builder()
            .type(ResponseFormatType.JSON)
            .jsonSchema(JsonSchema.builder()
                .name("response")
                .rootElement(JsonArraySchema.builder()
                    .description("생성한 문제 리스트")
                    .items(JsonObjectSchema.builder()
                        .addStringProperty("passageText","지문")
                        .addStringProperty("answer","정답")
                        .addStringProperty("level","해당하는 CEFR 레벨")
                        .addProperty("options", JsonArraySchema.builder()
                            .description("문법 문제의 선택지들")
                            .items(JsonStringSchema.builder().build())
                            .build())
                        .required("passageText", "answer", "level", "options")
                        .build())
                    .build())
                .build())
            .build()

        val request = ChatRequest.builder()
            .messages(UserMessage(prompt))
            .temperature(0.8)
            .responseFormat(responseFormat)
            .build()

        val response = chatModel.chat(request)
        val questions = objectMapper.readValue(response.aiMessage().text(), object : TypeReference<List<GrammarTestQuestion>>() {})

        return questions
    }

    fun generateReadingTests(): List<ReadingTestQuestion>{
        val prompt = """
            학습자의 정확한 CEFR 레벨(A1.1~C2.3)을 파악하기 위한 독해 문제를 생성해야 합니다.
            CEFR 레벨에 대한 설명은 아래 Level Description을 참고하세요.
            <Level Description>
                ${CEFRLevelPrompt.Description}
            </Level Description>
            ${LevelTestGuidePrompt.getReadingTestSystemPrompt()}
        """.trimIndent()

        val responseFormat = ResponseFormat.builder()
            .type(ResponseFormatType.JSON)
            .jsonSchema(JsonSchema.builder()
                .name("response")
                .rootElement(JsonArraySchema.builder()
                    .description("생성한 문제 리스트")
                    .items(JsonObjectSchema.builder()
                        .addStringProperty("passageText","지문")
                        .addStringProperty("answer","정답")
                        .addStringProperty("level","해당하는 CEFR 레벨")
                        .addStringProperty("question","질문")
                        .required("passageText", "answer", "level", "question")
                        .build())
                    .build())
                .build())
            .build()

        val request = ChatRequest.builder()
            .messages(UserMessage(prompt))
            .temperature(0.8)
            .responseFormat(responseFormat)
            .build()

        val response = chatModel.chat(request)
        val questions = objectMapper.readValue(response.aiMessage().text(), object : TypeReference<List<ReadingTestQuestion>>() {})

        return questions
    }
}