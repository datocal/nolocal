package com.datocal.nolocal.framework.commands

import com.datocal.nolocal.framework.discord.model.Interaction
import com.datocal.nolocal.framework.discord.model.InteractionResponse

interface DiscordCommandRunner {

    fun accept(interaction: Interaction): InteractionResponse
}
