package com.datocal.nolocal.infrastructure.discord.configuration

import com.datocal.nolocal.infrastructure.discord.client.DiscordApiClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@ConfigurationProperties("discord.api")
class DiscordConfiguration {

    @Autowired
    private lateinit var environment: Environment

    lateinit var url: String
    lateinit var secret: String
    lateinit var clientId: String

    @Bean
    fun apiClient(webclient: WebClient): DiscordApiClient {
        return DiscordApiClient(webclient)
    }

    @Bean
    fun webCLient(): WebClient {
        return WebClient
            .builder()
            .baseUrl("$url/{client-id}")
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bot $secret")
            .defaultUriVariables(mapOf("client-id" to clientId))
            .build()
    }
}
