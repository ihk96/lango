package com.inhyuk.lango

import com.inhyuk.lango.llm.model.ModelConfig

class ModelConfigForTest {

    companion object {
        private val config = ConfigurationLoader.loadConfig("application-local.yml")
        private val apiKey = ((config["llm"] as Map<String, Any>)["api"] as Map<String, Any>)["key"] as String
        private val modelConfig = ModelConfig(apiKey)

        val chatModel = modelConfig.fastChatModel()
        val streamingChatModel = modelConfig.fastStreamingChatModel()

    }
}