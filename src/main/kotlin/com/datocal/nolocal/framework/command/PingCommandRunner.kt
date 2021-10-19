package com.datocal.nolocal.framework.command

import com.datocal.nolocal.domain.dummy.Ping
import com.datocal.nolocal.framework.DEFAULT_COMMAND
import com.datocal.nolocal.framework.getCommandFromRawMessage
import org.javacord.api.event.message.MessageCreateEvent
import org.springframework.stereotype.Component

@Component(DEFAULT_COMMAND)
class PingCommandRunner(private val ping: Ping) : DiscordCommandRunner {

    override fun accept(event: MessageCreateEvent) {
        val command = getCommandFromRawMessage(event.messageContent)
        event.channel.sendMessage(ping.ping(request = command))
    }
}
