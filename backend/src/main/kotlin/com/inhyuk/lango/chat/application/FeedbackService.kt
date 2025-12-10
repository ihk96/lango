package com.inhyuk.lango.chat.application

import com.inhyuk.lango.chat.dto.ChatMessageResponse
import com.inhyuk.lango.llm.prompt.PromptManager
import dev.langchain4j.model.chat.ChatModel
import org.springframework.stereotype.Service

@Service
class FeedbackService(
    private val chatModel: ChatModel,
    private val promptManager: PromptManager
) {
    fun getFeedback(message: String, context: String? = "No context provided"): ChatMessageResponse {
        val prompt = promptManager.getFeedbackPrompt(message, context ?: "")
        val response = chatModel.chat(prompt)

        return ChatMessageResponse(response)
    }

    fun translate(text: String): ChatMessageResponse {
        val prompt = promptManager.getTranslationPrompt(text)
        val response = chatModel.chat(prompt)

        return ChatMessageResponse(response)
    }
}
