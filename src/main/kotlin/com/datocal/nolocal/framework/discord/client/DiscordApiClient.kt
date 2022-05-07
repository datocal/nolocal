package com.datocal.nolocal.framework.discord.client

import com.datocal.nolocal.framework.discord.model.ApplicationCommand

interface DiscordApiClient {

    fun getCommands(): List<ApplicationCommand> {
        return emptyList()
    }

    fun register(applicationCommand: ApplicationCommand) {
    }
}
