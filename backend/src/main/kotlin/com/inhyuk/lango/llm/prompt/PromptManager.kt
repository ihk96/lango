package com.inhyuk.lango.llm.prompt

import com.inhyuk.lango.user.domain.UserRole
import org.springframework.stereotype.Component

@Component
class PromptManager {

    fun getScenarioGenerationPrompt(level: String, topic: String? = null): String {
        val topicClause = topic?.let { "주제는 '$it'입니다." } ?: "사용자 수준에 적합한 일상 생활 주제를 무작위로 선택하세요."
        return """
            당신은 롤플레이 시나리오를 만드는 영어 튜터입니다.
            사용자의 영어 수준: $level
            $topicClause
            
            간단한 시나리오 설명과 당신이 맡을 역할과 사용자의 역할을 결정하세요.
            
            응답은 반드시 다음 JSON 형식으로만 작성하세요:
            {
                "scenario": "시나리오에 대한 설명...",
                "yourRole": "당신의 역할(예: Barista, Ticket Agent, Team Leader)",
                "userRole" : "사용자의 역할(예: Customer, Waiter, Team Member)",
            }
        """.trimIndent()
    }

    fun getChatSystemPrompt(aiRole: String, userRole: String, level: String, scenario: String): String {
        return """
            당신은 롤플레이 시나리오에서 '$aiRole' 역할을 맡고 있습니다.
            시나리오: $scenario
            
            사용자는 영어 학습자이며 수준은 $level 입니다.
            사용자의 역할은 $userRole
            
            당신의 목표는 그들의 수준에 맞춘 자연스러운 대화를 나누는 것입니다.
            - 반드시 영문으로 응답하세요.
            - 요청이 없는 한 대화 중 문법을 직접적으로 교정하지 마세요.
            - 응답은 간결하고 자연스럽게 유지하세요.
            - 사용자가 대화를 지속하도록 유도하세요.
        """.trimIndent()
    }

    fun getFeedbackPrompt(userMessage: String, context: String): String {
        return """
            다음 사용자 메시지를 대화 맥락에서 분석하세요.
            맥락: $context
            사용자 메시지: "$userMessage"
            
            다음 항목에 대해 피드백을 제공하세요:
            1. 문법의 정확성
            2. 표현의 자연스러움
            3. 필요하다면 더 좋은 대안 표현
            
            한국어로 답변하세요.
        """.trimIndent()
    }
    
    fun getTranslationPrompt(text: String): String {
        return """
            다음 영어 문장을 자연스러운 한국어로 번역하세요.
            부가적인 설명 없이 번역 결과만 응답하세요.
            텍스트: "$text"
        """.trimIndent()
    }

    fun getArticleGenerationPrompt(level: String, topic: String?): String {
        val topicClause =
            topic?.let { "주제: $it" } ?: "사용자 수준에 적합한 흥미로운 주제를 선택하세요."
        return """
            영어 학습자를 위한 영어 읽기 글을 생성하세요.
            사용자 수준: $level
            $topicClause
            
            글은 흥미로우면서 교육적이어야 합니다.
            본문에서 핵심 단어를 최소 3개를 선정하고 각 단어의 한국어 뜻을 함께 제시하세요.
            본문에서 핵심 표현을 최소 3개를 선정하고 각 단어의 한국어 뜻을 함께 제시하세요.
            
            형식은 반드시 다음 JSON으로만 작성하세요:
            {
                "title": "글 제목",
                "content": "전체 본문...",
                "vocabulary": [
                    { "word": "word1", "meaning": "한국어 뜻" },
                    ...
                ],
                "expressions": [
                    { "word": "expression1", "meaning": "한국어 뜻" },
                    ...
                ]
        """.trimIndent()
    }
    fun getLevelAssessmentPrompt(answers: String): String {
        return """
            다음 영어 평가 질문에 대한 사용자 답변을 분석하세요.
            사용자 답변:
            $answers
            
            문법, 어휘, 복잡성을 기반으로 사용자의 영어 수준을 결정하세요 (Beginner, Intermediate, Advanced).
            
            형식은 반드시 다음 JSON으로만 작성하세요:
            {
                "level": "Beginner/Intermediate/Advanced",
                "reason": "간단한 설명..."
            }
        """.trimIndent()
    }
}
