package com.inhyuk.lango.chat.presentation

import com.inhyuk.lango.chat.application.ChatService
import com.inhyuk.lango.chat.domain.ChatSessionEntity
import com.inhyuk.lango.chat.domain.MessageSender
import com.inhyuk.lango.chat.domain.ScenarioChatMessage
import com.inhyuk.lango.chat.dto.ChatSessionResponse
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.security.core.Authentication
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.UUID

class ChatControllerTest : BehaviorSpec({
    val chatService = mockk<ChatService>()
    val chatController = ChatController(chatService)
    val mockMvc = MockMvcBuilders.standaloneSetup(chatController).build()
    val userId = UUID.randomUUID().toString()
    val authentication = mockk<Authentication>()
    every { authentication.principal } returns userId
    every { authentication.name } returns "user1"

    given("A session generate request") {
        `when`("service returns session") {
            every { chatService.createSession(any(), any()) } returns
                    ChatSessionEntity(
                        userId, "Scenario", "User", "userRole", "aiRole",
                        userLevel = "A1.1",
                    )

            then("it should return 200 OK") {
                mockMvc.perform(post("/api/v1/chat/sessions")
                    .principal(authentication)
                    .queryParam("topic", "Coffee"))
                    .andExpect(status().isOk)
                    .andExpect { jsonPath("$.data.sessionId").isNotEmpty() }
                    .andExpect { jsonPath("$.data.scenario").isNotEmpty() }
                    .andExpect { jsonPath("$.data.title").isNotEmpty()}
                    .andExpect { jsonPath("$.data.aiRole").isNotEmpty() }
                    .andExpect { jsonPath("$.data.userRole").isNotEmpty() }
            }
        }
    }

    given("A session start request") {
        val sessionId = UUID.randomUUID().toString()
        `when`("service returns message") {
            every { chatService.startSessionChat(any()) } returns
                    ScenarioChatMessage(
                        message = "ai message",
                        translate = "인공지능 메시지",
                        code = 0
                    )

            then("it should return 200 OK") {
                mockMvc.perform(post("/api/v1/chat/sessions/$sessionId/start")
                    .principal(authentication))
                    .andExpect(status().isOk)
                    .andExpect { jsonPath("$.data.content").isNotEmpty() }
                    .andExpect { jsonPath("$.data.subContent").isNotEmpty() }
            }
        }
    }
})
