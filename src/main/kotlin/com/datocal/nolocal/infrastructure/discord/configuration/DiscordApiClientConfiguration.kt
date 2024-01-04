package com.datocal.nolocal.infrastructure.discord.configuration

import com.datocal.nolocal.infrastructure.discord.client.DiscordApplicationsClient
import com.datocal.nolocal.infrastructure.discord.client.DiscordWebhooksClient
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class DiscordApiClientConfiguration {

    @Bean
    fun applicationClient(@Qualifier("applicationWebClient") webclient: WebClient): DiscordApplicationsClient {
        return DiscordApplicationsClient(webclient)
    }

    @Bean
    fun webhooksClient(@Qualifier("webhooksWebClient") webclient: WebClient): DiscordWebhooksClient {
        return DiscordWebhooksClient(webclient)
    }

    @Bean("applicationWebClient")
    fun applicationWebClient(discordProperties: DiscordApiConfigurationProperties): WebClient {
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

    @Bean("webhooksWebClient")
    fun webhooksWebClient(discordProperties: DiscordApiConfigurationProperties): WebClient {
        return WebClient
            .builder()
            .baseUrl(discordProperties.fullWebHooksUrl)
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
