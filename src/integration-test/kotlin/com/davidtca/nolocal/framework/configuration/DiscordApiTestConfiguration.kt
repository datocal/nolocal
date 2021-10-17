package com.davidtca.nolocal.framework.configuration

import org.javacord.api.DiscordApi
import org.mockito.Mockito.mock
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("test")
class DiscordApiTestConfiguration {

    @Bean
    fun discordApi(): DiscordApi {
        return mock(DiscordApi::class.java)
    }
}
