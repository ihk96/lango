package com.inhyuk.lango.llm.prompt

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.string.shouldContain

class PromptManagerTest : StringSpec({
    val promptManager = PromptManager()

    "getScenarioGenerationPrompt should include level and topic" {
        val prompt = promptManager.getScenarioGenerationPrompt("Intermediate", "Coffee Shop")
        
        prompt shouldContain "Intermediate"
        prompt shouldContain "The topic is 'Coffee Shop'."
        prompt shouldContain "scenario" // JSON Key check
    }

    "getScenarioGenerationPrompt should handle null topic" {
        val prompt = promptManager.getScenarioGenerationPrompt("Beginner", null)
        
        prompt shouldContain "Beginner"
        prompt shouldContain "Choose a random daily life topic"
    }

    "getArticleGenerationPrompt should include level" {
        val prompt = promptManager.getArticleGenerationPrompt("Advanced", "AI Technology")
        
        prompt shouldContain "Advanced"
        prompt shouldContain "AI Technology"
        prompt shouldContain "vocabulary"
    }
})
