package com.datocal.nolocal.framework.command

import org.javacord.api.event.message.MessageCreateEvent

interface DiscordCommandRunner {

    fun accept(event: MessageCreateEvent)
}
