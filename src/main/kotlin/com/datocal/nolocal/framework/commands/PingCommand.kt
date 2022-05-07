package com.datocal.nolocal.framework.commands

import com.datocal.nolocal.domain.dummy.Ping
import com.datocal.nolocal.framework.discord.model.Command
import com.datocal.nolocal.framework.discord.model.Interaction
import com.datocal.nolocal.framework.discord.model.InteractionResponse
import org.springframework.stereotype.Component

@Component("culo")
@Command(
    command = "culo",
    description = "El culo tuyo",
    type = Command.TYPE_CHAT_INPUT,
)
class PingCommand(private val ping: Ping) : DiscordCommand {

    override fun accept(interaction: Interaction): InteractionResponse {
        return InteractionResponse(ping.ping())
    }
}
