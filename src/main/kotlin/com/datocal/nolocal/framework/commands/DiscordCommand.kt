package com.datocal.nolocal.framework.commands

import com.datocal.nolocal.framework.discord.model.Interaction
import com.datocal.nolocal.framework.discord.model.InteractionResponse
import com.datocal.nolocal.framework.discord.model.InteractionResponseData

interface DiscordCommand {

    fun accept(interaction: Interaction): InteractionResponse

    fun simpleMessage(message: String): InteractionResponse {
        return InteractionResponse(
            type = 4,
            data = InteractionResponseData(
                content = message,
            )
        )
    }
}
