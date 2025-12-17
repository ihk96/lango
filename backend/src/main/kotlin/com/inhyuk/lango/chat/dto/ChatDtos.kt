package com.inhyuk.lango.chat.dto

import com.inhyuk.lango.chat.domain.ChatSessionEntity
import com.inhyuk.lango.chat.domain.MessageSender

data class ScenarioGenerationResponse(
    val title : String,
    val scenario: String,
    val aiRole: String,
    val userRole: String
)

data class ChatSessionResponse(
    val sessionId: String?,
    val title: String,
    val scenario: String,
    val userRole: String,
    val aiRole: String,
) {
    companion object {
        fun from(session: ChatSessionEntity): ChatSessionResponse {
            return ChatSessionResponse(
                sessionId = session.id,
                scenario = session.scenario,
                userRole = session.userRole,
                aiRole = session.aiRole,
                title = session.title
            )
        }
    }
}

data class ChatMessageRequest(
    val content: String
)

data class ChatMessageResponse(
    val content: String,
    val subContent : String?,
    val sender: MessageSender
)