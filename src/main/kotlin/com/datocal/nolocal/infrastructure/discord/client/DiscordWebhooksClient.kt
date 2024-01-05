package com.datocal.nolocal.infrastructure.discord.client

import com.datocal.nolocal.infrastructure.discord.model.ApplicationCommand
import org.springframework.web.reactive.function.client.WebClient

class DiscordWebhooksClient(
    private val client: WebClient,
) {

    fun followup(
        interactionToken: String,
        request: FollowUpRequest,
    )  {
        client
            .post()
            .uri("/$interactionToken")
            .bodyValue(request)
            .retrieve()
            .toEntity(ApplicationCommand::class.java)
            .block()
    }
}

data class FollowUpRequest(
    val content: String,
)
