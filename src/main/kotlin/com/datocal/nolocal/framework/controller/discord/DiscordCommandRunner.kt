package com.datocal.nolocal.framework.controller.discord

import org.javacord.api.event.message.MessageCreateEvent

interface DiscordCommandRunner {

    fun accept(event: MessageCreateEvent)
}
