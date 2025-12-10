package com.inhyuk.lango.chat.presentation

import com.inhyuk.lango.chat.application.FeedbackService
import com.inhyuk.lango.chat.dto.ChatMessageResponse
import com.inhyuk.lango.common.dto.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class FeedbackRequest(val message: String, val context: String?)
data class TranslationRequest(val text: String)

@RestController
@RequestMapping("/api/support")
class LearningSupportController(
    private val feedbackService: FeedbackService
) {

    @PostMapping("/feedback")
    fun getFeedback(@RequestBody request: FeedbackRequest): ResponseEntity<ApiResponse<ChatMessageResponse>> {
        return ResponseEntity.ok(ApiResponse(feedbackService.getFeedback(request.message, request.context)))
    }

    @PostMapping("/translate")
    fun translate(@RequestBody request: TranslationRequest): ResponseEntity<ApiResponse<ChatMessageResponse>> {
        return ResponseEntity.ok(ApiResponse(feedbackService.translate(request.text)))
    }
}
