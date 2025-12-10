package com.inhyuk.lango.chat.presentation

import com.inhyuk.lango.chat.application.ChatService
import com.inhyuk.lango.chat.dto.ChatMessageRequest
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
        return ResponseEntity.ok(ApiResponse(data=chatService.startSession(userId, topic)))
    }

    @PostMapping("/sessions/{sessionId}/messages/stream", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun streamMessage(
        @PathVariable sessionId: Long,
        @RequestBody request: ChatMessageRequest
    ): SseEmitter {
        val emitter = SseEmitter(60000L) // 1 minute timeout
        
        // Run in separate thread usually handled by Async support or manual execution
        // SseEmitter can be handled by standard MVC. However, the service logic calls streaming model which is async.
        // We just call the service method which triggers the streaming.
        // Important: streamMessage in service should NOT block. 
        // My implementation of ChatService.streamMessage calls streamingChatModel.generate(...) which IS async/non-blocking usually.
        
        chatService.streamMessage(sessionId, request.content, emitter)
        
        return emitter
    }
}
