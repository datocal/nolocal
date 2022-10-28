package com.datocal.nolocal.infrastructure.discord.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("discord.api")
class DiscordApiConfigurationProperties {

    lateinit var host: String
    var port: String? = null
    lateinit var baseEndpoint: String
    lateinit var secret: String
    lateinit var clientId: String
    lateinit var guildId: String

    @Value("\${spring.profiles.active}")
    lateinit var activeProfile: String

    val fullBaseUrlWithPlaceholders: String
        get() = "$host${customPort()}$endpointWithPlaceholders"
    val endpointWithPlaceholders: String
        get() = "$baseEndpoint/{client-id}${extendWithPath()}"
    val endpoint: String
        get() = endpointWithPlaceholders
            .replace("{client-id}", clientId)
            .replace("{guild-id}", guildId)

    private fun extendWithPath(): String {
        if (activeProfile == "dev") {
            return "/guilds/{guild-id}"
        }
        return ""
    }

    private fun customPort(): String {
        return if (port == null) ""
        else ":$port"
    }
}
