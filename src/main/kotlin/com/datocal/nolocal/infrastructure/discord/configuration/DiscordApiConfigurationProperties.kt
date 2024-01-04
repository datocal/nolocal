package com.datocal.nolocal.infrastructure.discord.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class DiscordApiConfigurationProperties {

    @Value("\${discord.host}")
    lateinit var host: String

    @Value("\${discord.port:#{null}}")
    var port: String? = null

    @Value("\${discord.base-endpoint}")
    lateinit var baseEndpoint: String

    @Value("\${discord.secret}")
    lateinit var secret: String

    @Value("\${discord.applications-api.base-endpoint}")
    lateinit var applicationsBaseEndpoint: String

    @Value("\${discord.applications-api.client-id}")
    lateinit var clientId: String

    @Value("\${discord.applications-api.guild-id}")
    lateinit var guildId: String

    @Value("\${discord.applications-api.guild-mode:true}")
    var guildMode: Boolean = true

    @Value("\${discord.webhooks-api.base-endpoint}")
    lateinit var webhooksBaseEndpoint: String

    private val portAppender: String
        get() = port?.let { ":$it" } ?: ""

    private val fullHost: String
        get() = "$host$portAppender"

    private val guildPath: String
        get() = if (guildMode) "/guilds/{guild-id}" else ""

    private val parameterApplicationsEndpoint: String
        get() = "$baseEndpoint$applicationsBaseEndpoint/{client-id}$guildPath"

    val fullApplicationsUrl: String
        get() = "$fullHost$parameterApplicationsEndpoint"

    val fullWebHooksUrl: String
        get() = "$fullHost$baseEndpoint$webhooksBaseEndpoint/{client-id}"

    val endpoint: String
        get() = parameterApplicationsEndpoint
            .replace("{client-id}", clientId)
            .replace("{guild-id}", guildId)
}
