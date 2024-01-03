package com.datocal.nolocal.infrastructure.discord.configuration

import com.datocal.nolocal.infrastructure.discord.client.DiscordApiClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class DiscordApiClientConfiguration {

    @Bean
    fun apiClient(webclient: WebClient): DiscordApiClient {
        return DiscordApiClient(webclient)
    }

    @Bean
    @ConditionalOnMissingBean
    fun webCLient(discordProperties: DiscordApiConfigurationProperties): WebClient {
        return WebClient
            .builder()
            .baseUrl(discordProperties.fullApplicationsUrl)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bot ${discordProperties.secret}")
            .defaultUriVariables(
                mapOf(
                    "client-id" to discordProperties.clientId,
                    "guild-id" to discordProperties.guildId,
                ),
            )
            .build()
    }
}
