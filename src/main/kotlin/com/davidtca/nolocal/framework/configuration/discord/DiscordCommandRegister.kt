package com.davidtca.nolocal.framework.configuration.discord

import org.javacord.api.DiscordApi
import org.springframework.stereotype.Service


@Service
class DiscordCommandRegister(discordApi: DiscordApi) {

    init {
        discordApi.addMessageCreateListener { event ->
            if (event.messageContent.equals("+ping", ignoreCase = true)){
                event.channel.sendMessage("Pong!")
            }
        }
    }
}