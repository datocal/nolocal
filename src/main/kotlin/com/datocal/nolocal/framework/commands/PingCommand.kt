package com.datocal.nolocal.framework.commands

import com.datocal.nolocal.domain.dummy.Ping
import com.datocal.nolocal.framework.discord.model.Interaction
import com.datocal.nolocal.framework.discord.model.InteractionResponse
import org.springframework.stereotype.Component

@Component("culo")
class PingCommand(private val ping: Ping) : DiscordCommand {

    override fun accept(interaction: Interaction): InteractionResponse {
        return simpleMessage(ping.ping())
    }
}
