package com.inhyuk.lango.chat.application

import com.inhyuk.lango.llm.prompt.PromptManager
import dev.langchain4j.model.chat.ChatLanguageModel
import org.springframework.stereotype.Service

@Service
class FeedbackService(
    private val chatModel: ChatLanguageModel,
    private val promptManager: PromptManager
) {
    fun getFeedback(message: String, context: String? = "No context provided"): String {
        val prompt = promptManager.getFeedbackPrompt(message, context ?: "")
        return chatModel.generate(prompt)
    }

    fun translate(text: String): String {
        val prompt = promptManager.getTranslationPrompt(text)
        return chatModel.generate(prompt)
    }
}
