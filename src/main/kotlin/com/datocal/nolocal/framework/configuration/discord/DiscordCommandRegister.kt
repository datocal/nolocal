package com.datocal.nolocal.framework.configuration.discord

import com.datocal.nolocal.framework.DEFAULT_COMMAND
import com.datocal.nolocal.framework.command.DiscordCommandRunner
import com.datocal.nolocal.framework.getCommandFromRawMessage
import com.datocal.nolocal.framework.rawMessageContainsCommand
import org.javacord.api.DiscordApi
import org.springframework.context.annotation.Configuration

@Configuration
class DiscordCommandRegister(
    // This will use a map created by spring with the beans for the commands
    private val registry: Map<String, DiscordCommandRunner>,
    private val discordApi: DiscordApi,
) {

    init {
        registerMessages()
    }

    private fun registerMessages() {
        discordApi.addMessageCreateListener { event ->
            if (rawMessageContainsCommand(event.messageContent)) {
                (registry[getCommandFromRawMessage(event.messageContent)] ?: registry[DEFAULT_COMMAND])?.accept(event)
            }
        }
    }
}
