package com.datocal.nolocal.framework.commands

import com.datocal.nolocal.framework.discord.model.Interaction
import com.datocal.nolocal.framework.discord.model.InteractionResponse

interface DiscordCommand {

    fun accept(interaction: Interaction): InteractionResponse
}
