package com.inhyuk.lango.common.config

import org.flywaydb.core.internal.util.ObjectMapperFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import tools.jackson.databind.ObjectMapper

@Configuration
class WebConfig : WebMvcConfigurer {

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
    }


}