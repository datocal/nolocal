package com.datocal.nolocal.infrastructure.commands

import com.datocal.nolocal.infrastructure.discord.model.Command
import com.datocal.nolocal.infrastructure.discord.model.Interaction
import com.datocal.nolocal.infrastructure.discord.model.InteractionResponse
import com.datocal.nolocal.infrastructure.messaging.server.CreateCommandPublisher

@Command(
    command = Commands.CREATE,
    description = "WIP - Creates an instance",
    type = Command.TYPE_CHAT_INPUT,
)
class CreateCommand(
    private val commandPublisher: CreateCommandPublisher,
) : DiscordCommand {
    override fun accept(interaction: Interaction): InteractionResponse {
        commandPublisher.publish(interaction)
        return InteractionResponse("Working on it")
    }
}
