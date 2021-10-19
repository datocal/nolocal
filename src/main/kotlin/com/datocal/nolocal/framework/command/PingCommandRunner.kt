package com.datocal.nolocal.framework.command

import com.datocal.nolocal.domain.dummy.Ping
import com.datocal.nolocal.framework.command
import org.javacord.api.event.message.MessageCreateEvent

class PingCommandRunner(private val ping: Ping) : DiscordCommandRunner {

    override fun accept(event: MessageCreateEvent) {
        val command = command(event.messageContent)
        event.channel.sendMessage(ping.ping(request = command))
    }
}
