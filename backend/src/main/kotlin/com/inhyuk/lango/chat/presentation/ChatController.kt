package com.inhyuk.lango.chat.presentation

import com.inhyuk.lango.chat.application.ChatService
import com.inhyuk.lango.chat.dto.ChatMessageRequest
import com.inhyuk.lango.chat.dto.ChatMessageResponse
import com.inhyuk.lango.chat.dto.ChatSessionResponse
import com.inhyuk.lango.common.dto.ApiResponse
import dev.langchain4j.model.chat.response.ChatResponse
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.security.Principal

@RestController
@RequestMapping("/api/v1/chat")
class ChatController(
    private val chatService: ChatService
) {

    @GetMapping("/sessions")
    fun getSessions(
        authentication: Authentication
    ): ResponseEntity<ApiResponse<List<ChatSessionResponse>>> {
        val userId = authentication.principal as? String ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        val sessions = chatService.getChatSessions(userId)
        return ResponseEntity.ok(ApiResponse(data = sessions.map { ChatSessionResponse.from(it) }))
    }

    @GetMapping("/sessions/{sessionId}")
    fun getSession(@PathVariable sessionId: String): ResponseEntity<ApiResponse<ChatSessionResponse>> {
        val session = chatService.getChatSession(sessionId) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(ApiResponse(ChatSessionResponse.from(session)))
    }

    @GetMapping("/sessions/{sessionId}/history")
    fun getSessionHistory(@PathVariable sessionId: String): ResponseEntity<ApiResponse<List<ChatMessageResponse>>> {
        val history = chatService.getChatHistory(sessionId)
        return ResponseEntity.ok(ApiResponse(data = history.map { ChatMessageResponse(content = it.content, subContent = it.subContent) }))
    }

    @PostMapping("/sessions")
    fun createSession(
        @RequestParam(required = false) topic: String?,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<ChatSessionResponse>> {
        val userId = authentication.principal as? String ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        val sessionEntity = chatService.createSession(userId, topic) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(ApiResponse(data=ChatSessionResponse.from(sessionEntity)))
    }

    @PostMapping("/sessions/{sessionId}/start")
    fun startSession(@PathVariable sessionId: String): ResponseEntity<ApiResponse<ChatMessageResponse>> {
        val message = chatService.startSessionChat(sessionId)
        return ResponseEntity.ok(ApiResponse(ChatMessageResponse(content = message.message, subContent = message.translate)))
    }

    @PostMapping("/sessions/{sessionId}/messages")
    fun chatMessage(
        @PathVariable sessionId: String,
        @RequestBody request: ChatMessageRequest,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<ChatMessageResponse>> {
        val userId = authentication.principal as? String ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        val message = chatService.chatMessage(userId, sessionId, request.content)
        return ResponseEntity.ok(ApiResponse(ChatMessageResponse(content = message.message, subContent = message.translate)))
    }
}
