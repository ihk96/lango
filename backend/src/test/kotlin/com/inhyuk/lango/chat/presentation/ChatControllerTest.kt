package com.inhyuk.lango.chat.presentation

import com.inhyuk.lango.chat.application.ChatService
import com.inhyuk.lango.chat.dto.ChatSessionResponse
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ChatControllerTest : BehaviorSpec({
    val chatService = mockk<ChatService>()
    val chatController = ChatController(chatService)
    val mockMvc = MockMvcBuilders.standaloneSetup(chatController).build()

    given("A session start request") {
        `when`("service returns session") {
            every { chatService.createSession(any(), any()) } returns
                ChatSessionResponse(1L, "Scenario", "User", "AI")

            then("it should return 200 OK") {
                // Principal mocking is required for standard flows, but standalone setup bypasses security filters unless configured.
                // However, the controller method parameter `Principal` will be null if not injected.
                // MockMvcRequestBuilders allow setting principal.
                
                mockMvc.perform(post("/api/chat/sessions")
                    .principal { "test@test.com" }
                    .queryParam("topic", "Coffee"))
                    .andExpect(status().isOk)
            }
        }
    }
})
