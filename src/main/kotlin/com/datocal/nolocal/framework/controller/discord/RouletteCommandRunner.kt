package com.datocal.nolocal.framework.controller.discord

import org.javacord.api.event.message.MessageCreateEvent

class RouletteCommandRunner : DiscordCommandRunner {

    override fun accept(event: MessageCreateEvent) {
        event.channel.sendMessage(event.messageContent.removePrefix("+roulette").lines().filter { !it.contains("~~") }.random())
    }
}
