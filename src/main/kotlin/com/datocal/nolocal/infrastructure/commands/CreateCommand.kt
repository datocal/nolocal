package com.datocal.nolocal.infrastructure.commands

import com.datocal.nolocal.domain.dummy.Ping
import com.datocal.nolocal.infrastructure.discord.client.DiscordApplicationsClient
import com.datocal.nolocal.infrastructure.discord.client.DiscordWebhooksClient
import com.datocal.nolocal.infrastructure.discord.client.FollowUpRequest
import com.datocal.nolocal.infrastructure.discord.model.Command
import com.datocal.nolocal.infrastructure.discord.model.Interaction
import com.datocal.nolocal.infrastructure.discord.model.InteractionResponse
import org.springframework.stereotype.Component
import kotlinx.coroutines.*
@Component(Commands.CREATE)
@Command(
    command = Commands.CREATE,
    description = "WIP - Creates an instance",
    type = Command.TYPE_CHAT_INPUT,
)
class CreateCommand(private val ping: Ping, private val apiClient: DiscordWebhooksClient) : DiscordCommand {
    override fun accept(interaction: Interaction): InteractionResponse {
        GlobalScope.launch {
            delay(8000) // Delay for 2000 milliseconds (2 seconds)
            apiClient.followup(interaction.token, FollowUpRequest(content = "Hello again, Testing followup"))
        }
        return InteractionResponse(ping.ping())
    }
}
