package com.datocal.nolocal.infrastructure.commands

import com.datocal.nolocal.infrastructure.discord.model.Interaction
import com.datocal.nolocal.infrastructure.discord.model.InteractionResponse

interface DiscordCommand {
    fun accept(interaction: Interaction): InteractionResponse
}
