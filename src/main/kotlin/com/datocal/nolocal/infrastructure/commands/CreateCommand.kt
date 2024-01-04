package com.datocal.nolocal.infrastructure.commands

import com.datocal.nolocal.domain.dummy.Ping
import com.datocal.nolocal.infrastructure.discord.client.DiscordApplicationsClient
import com.datocal.nolocal.infrastructure.discord.model.Command
import com.datocal.nolocal.infrastructure.discord.model.Interaction
import com.datocal.nolocal.infrastructure.discord.model.InteractionResponse
import org.springframework.stereotype.Component

@Component(Commands.CREATE)
@Command(
    command = Commands.CREATE,
    description = "WIP - Creates an instance",
    type = Command.TYPE_CHAT_INPUT,
)
class CreateCommand(private val ping: Ping, private val apiClient: DiscordApplicationsClient) : DiscordCommand {
    override fun accept(interaction: Interaction): InteractionResponse {
        return InteractionResponse(ping.ping())
    }
}
