package com.datocal.nolocal.infrastructure.messaging.server

import com.datocal.nolocal.application.server.CreateServerRequest
import com.datocal.nolocal.application.server.CreateServerUseCase
import com.datocal.nolocal.infrastructure.discord.client.DiscordWebhooksClient
import com.datocal.nolocal.infrastructure.discord.client.FollowUpRequest
import org.springframework.context.event.EventListener

class CreateCommandListener(
    private val createServerUseCase: CreateServerUseCase,
    private val discordWebhooksClient: DiscordWebhooksClient,
) {
    @EventListener
    fun handleContextStart(request: CreateRequest) {
        createServerUseCase.execute(request.toUseCaseRequest())
        discordWebhooksClient.followup(
            request.token,
            FollowUpRequest(
                content = "Hey there! This is a followup to the command",
            ),
        )
    }
}

private fun CreateRequest.toUseCaseRequest() = CreateServerRequest()
