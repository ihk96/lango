package com.inhyuk.lango.common.exception

import com.inhyuk.lango.common.dto.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<Unit>> {
        // TODO: Log the exception properly with SLF4J
        return ResponseEntity.internalServerError()
            .body(ApiResponse(null,e.message ?: "An unknown error occurred"))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ApiResponse<Unit>> {
        return ResponseEntity.badRequest()
            .body(ApiResponse(null,e.message ?: "Invalid argument"))
    }
}
