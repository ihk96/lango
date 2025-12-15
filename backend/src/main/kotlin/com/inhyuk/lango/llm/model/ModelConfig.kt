package com.inhyuk.lango.llm.model

import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.chat.StreamingChatModel
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel
import dev.langchain4j.model.googleai.GoogleAiGeminiStreamingChatModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ModelConfig(
    @Value("\${llm.api.key}")
    private var apiKey: String=""
) {

    @Bean
    fun chatModel(): ChatModel {
        val model = GoogleAiGeminiChatModel.builder()
            .apiKey(apiKey)
            .modelName("gemini-3-pro-preview")
            .returnThinking(true)
            .build()
        return model
    }

    @Bean
    fun fastChatModel() : ChatModel {
        val model = GoogleAiGeminiChatModel.builder()
            .apiKey(apiKey)
            .modelName("gemini-2.5-flash")
            .returnThinking(true)
            .build()
        return model
    }

    @Bean
    fun streamingChatModel() : StreamingChatModel {
        val model = GoogleAiGeminiStreamingChatModel.builder()
            .apiKey(apiKey)
            .modelName("gemini-3-pro-preview")
            .returnThinking(true)
            .build()
        return model
    }

    @Bean
    fun fastStreamingChatModel() : StreamingChatModel {
        val model = GoogleAiGeminiStreamingChatModel.builder()
            .apiKey(apiKey)
            .modelName("gemini-2.5-flash")
            .returnThinking(true)
            .build()
        return model
    }
}