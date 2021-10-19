package com.datocal.nolocal.framework.configuration.discord

import com.datocal.nolocal.framework.command
import com.datocal.nolocal.framework.command.DiscordCommandRunner
import com.datocal.nolocal.framework.isCommand
import org.javacord.api.DiscordApi

class DiscordCommandRegister(
    private val registry: Map<String, DiscordCommandRunner>,
    private val discordApi: DiscordApi,
) {

    init {
        registerMessages()
    }

    private fun registerMessages() {
        discordApi.addMessageCreateListener { event ->
            if (isCommand(event.messageContent)) {
                (registry[command(event.messageContent)] ?: registry["default"])?.accept(event)
            }
        }
    }
}
