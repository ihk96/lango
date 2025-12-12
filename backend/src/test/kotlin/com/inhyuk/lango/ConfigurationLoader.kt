package com.inhyuk.lango

import org.yaml.snakeyaml.Yaml

class ConfigurationLoader {

    companion object {
        fun loadConfig(fileName: String): Map<String, Any> {
            val yaml = Yaml()

            val contextClassLoader = ConfigurationLoader::class.java.classLoader

            val stream = contextClassLoader
                .getResourceAsStream(fileName)
                ?: throw IllegalArgumentException("$fileName not found")

           return yaml.load(stream)
        }
    }
}