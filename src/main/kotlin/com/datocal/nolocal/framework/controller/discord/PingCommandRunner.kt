package com.datocal.nolocal.framework.controller.discord

import com.datocal.nolocal.framework.command
import org.javacord.api.event.message.MessageCreateEvent

class PingCommandRunner : DiscordCommandRunner {

    override fun accept(event: MessageCreateEvent) {
        val command = command(event.messageContent)
        event.channel.sendMessage("El $command tuyo")
    }
}
