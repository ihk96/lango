package com.inhyuk.lango.llm.model

import com.inhyuk.lango.ConfigurationLoader
import io.kotest.core.spec.style.BehaviorSpec

class ModelTests : BehaviorSpec({

    val config = ConfigurationLoader.loadConfig("application-local.yml")
    val apiKey = ((config["llm"] as Map<String, Any>)["api"] as Map<String, Any>)["key"] as String

    given("Chat model test") {

        val modelConfig = ModelConfig(apiKey)
        val chatModel = modelConfig.fastChatModel()

        `when`("test") {
            then("test") {
                val response = chatModel.chat("안녕하세요.")
                print(response)
            }
        }
    }
})