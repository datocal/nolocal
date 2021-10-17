package com.davidtca.nolocal.framework.configuration.discord

import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class DiscordConfiguration {

    @Value("\${secrets.discord.token}")
    private lateinit var token: String

    @Bean
    fun discordApi(): DiscordApi {
        return DiscordApiBuilder().setToken(token).login().join()
    }

}