package com.inhyuk.lango.level.application

import com.inhyuk.lango.level.domain.CEFRLevel
import com.inhyuk.lango.level.domain.Levels
import com.inhyuk.lango.level.domain.LevelTestAnswer
import com.inhyuk.lango.level.prompt.LevelTestEvalGuidePrompt
import dev.langchain4j.data.message.UserMessage
import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.chat.request.ChatRequest
import dev.langchain4j.model.chat.request.ResponseFormat
import dev.langchain4j.model.chat.request.ResponseFormatType
import dev.langchain4j.model.chat.request.json.JsonSchema
import dev.langchain4j.model.chat.request.json.JsonStringSchema
import org.springframework.stereotype.Component

@Component
class LevelTestEvaluationService(
    private val chatModel: ChatModel,
) {
    
    fun evaluate(answer: LevelTestAnswer): CEFRLevel {
        
        val prompt = LevelTestEvalGuidePrompt.getTestEvaluationSystemPrompt(answer)
        val responseFormat = ResponseFormat.builder()
            .type(ResponseFormatType.JSON)
            .jsonSchema(JsonSchema.builder()
                .name("response")
                .rootElement(
                    JsonStringSchema.builder()
                        .description("판정된 종합 CEFR 레벨. A1.1, A1.2, A1.3, A2.1, A2.2, A2.3, B1.1, B1.2, B1.3, B2.1, B2.2, B2.3, C1.1, C1.2, C1.3, C2.1, C2.2, C2.3 중 하나")
                        .build())
                .build())
            .build()
        
        val request = ChatRequest.builder()
            .messages(UserMessage(prompt))
            .temperature(0.2)
            .responseFormat(responseFormat)
            .build()
        
        val response = chatModel.chat(request)
        val levelText = response.aiMessage().text().replace("\"", "")

        val level = Levels.findLevel(levelText.trim())

        return level ?: throw IllegalStateException(
            "Level evaluation failed. Response: $levelText"
        )
    }

}