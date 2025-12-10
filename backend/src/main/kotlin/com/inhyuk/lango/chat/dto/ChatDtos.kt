package com.inhyuk.lango.chat.dto

import com.inhyuk.lango.chat.domain.ChatSession

data class ScenarioGenerationResponse(
    val scenario: String,
    val yourRole: String,
    val userRole: String
)

data class ChatSessionResponse(
    val sessionId: Long,
    val scenario: String,
    val userRole: String,
    val aiRole: String,
) {
    companion object {
        fun from(session: ChatSession): ChatSessionResponse {
            return ChatSessionResponse(
                sessionId = session.id!!,
                scenario = session.scenario,
                userRole = session.userRole,
                aiRole = session.aiRole
            )
        }
    }
}

data class ChatMessageRequest(
    val content: String
)
