package com.davidtca.nolocal.framework.controller.discord

import org.javacord.api.event.message.MessageCreateEvent

interface DiscordCommandRunner {

    fun accept(event: MessageCreateEvent)
}
