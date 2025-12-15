package com.inhyuk.lango.level.presentation

import com.inhyuk.lango.level.application.LevelService
import com.inhyuk.lango.level.domain.UserLevelEntity
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.security.core.Authentication
import java.time.LocalDateTime

class UsersLevelControllerTest : BehaviorSpec({
    val levelService = mockk<LevelService>()
    val controller = UsersLevelController(levelService)
    val authentication = mockk<Authentication>()
    every { authentication.principal } returns "user1"
    every { authentication.name } returns "user1"

    val mockMvc = MockMvcBuilders.standaloneSetup(controller)
        .build()

    given("사용자 레벨 조회") {
        `when`("서비스가 사용자 레벨을 반환") {
            val entity = mockk<UserLevelEntity>()
            every { entity.id } returns "lvl-1"
            every { entity.userId } returns "test@test.com"
            every { entity.level } returns "A1.1"
            every { entity.createdAt } returns LocalDateTime.now()
            every { entity.updatedAt } returns LocalDateTime.now()

            every { levelService.getUserslevel(any()) } returns entity

            then("200 OK 와 ApiResponse 포맷으로 응답") {
                mockMvc.perform(
                    get("/api/v1/users/levels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(authentication)
                )
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.data.userId").value("test@test.com"))
                    .andExpect(jsonPath("$.data.level.level").value("A1.1"))
            }
        }
    }
})
