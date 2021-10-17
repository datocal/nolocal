package com.davidtca.nolocal.framework.configuration.discord

import com.davidtca.nolocal.framework.command
import com.davidtca.nolocal.framework.controller.discord.DiscordCommandRunner
import com.davidtca.nolocal.framework.isCommand
import org.javacord.api.DiscordApi
import org.springframework.context.annotation.Configuration

@Configuration
class DiscordCommandRegister(
    private val registry: Map<String, DiscordCommandRunner>,
    discordApi: DiscordApi,
) {

    init {
        discordApi.addMessageCreateListener { event ->
            if (isCommand(event.messageContent)) {
                registry[command(event.messageContent)]?.accept(event)
            }
        }
    }
}
