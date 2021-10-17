package com.davidtca.nolocal.framework.configuration.discord

import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("production")
class DiscordApiConfiguration {

    @Value("\${secrets.discord.token}")
    private lateinit var token: String

    @Bean(destroyMethod = "disconnect")
    fun discordApi(): DiscordApi {
        return DiscordApiBuilder().setToken(token).login().join()
    }
}
