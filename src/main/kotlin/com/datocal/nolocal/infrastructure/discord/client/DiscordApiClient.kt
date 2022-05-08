package com.datocal.nolocal.infrastructure.discord.client

import com.datocal.nolocal.infrastructure.discord.model.ApplicationCommand

interface DiscordApiClient {

    fun getCommands(): List<ApplicationCommand> {
        return emptyList()
    }

    fun register(applicationCommand: ApplicationCommand) {
    }
}
