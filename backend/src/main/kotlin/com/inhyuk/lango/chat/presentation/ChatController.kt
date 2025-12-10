package com.inhyuk.lango.chat.presentation

import com.inhyuk.lango.chat.application.ChatService
import com.inhyuk.lango.chat.dto.ChatMessageRequest
import com.inhyuk.lango.chat.dto.ChatMessageResponse
import com.inhyuk.lango.chat.dto.ChatSessionResponse
import com.inhyuk.lango.common.dto.ApiResponse
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.security.Principal

@RestController
@RequestMapping("/api/chat")
class ChatController(
    private val chatService: ChatService
) {

    @PostMapping("/sessions")
    fun startSession(
        @RequestParam(required = false) topic: String?,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<ChatSessionResponse>> {
        val userId = authentication.principal as? String ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        return ResponseEntity.ok(ApiResponse(data=chatService.createSession(userId, topic)))
    }

    @PostMapping("/sessions/{sessionId}/messages")
    fun chatMessage(
        @PathVariable sessionId: Long,
        @RequestBody request: ChatMessageRequest
    ): ResponseEntity<ApiResponse<ChatMessageResponse>> {

        return ResponseEntity.ok(ApiResponse(data=ChatMessageResponse("test")))
    }
}
