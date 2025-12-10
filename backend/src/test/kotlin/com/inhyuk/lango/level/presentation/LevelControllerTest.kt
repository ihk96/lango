package com.inhyuk.lango.level.presentation

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.inhyuk.lango.level.application.LevelService
import com.inhyuk.lango.level.dto.AssessmentRequest
import com.inhyuk.lango.level.dto.AssessmentResponse
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class LevelControllerTest : BehaviorSpec({
    val levelService = mockk<LevelService>()
    val levelController = LevelController(levelService)
    val mockMvc = MockMvcBuilders.standaloneSetup(levelController).build()
    val objectMapper = jacksonObjectMapper()

    given("An assessment request") {
        val request = AssessmentRequest("Answers")
        
        `when`("service returns assessment") {
            every { levelService.assessInitialLevel(any(), any()) } returns 
                AssessmentResponse("Advanced", "Reason")

            then("it should return 200 OK") {
                mockMvc.perform(post("/api/level/assess-initial")
                    .principal { "test@test.com" }
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk)
            }
        }
    }
})
