package com.datocal.nolocal.infrastructure.commands

import com.datocal.nolocal.application.SubstituteRandomCharactersByDsUseCase
import com.datocal.nolocal.infrastructure.discord.model.Command
import com.datocal.nolocal.infrastructure.discord.model.Interaction
import com.datocal.nolocal.infrastructure.discord.model.InteractionResponse
import org.springframework.stereotype.Component

@Component("deto")
@Command(
    command = "deto",
    type = Command.TYPE_MESSAGE,
)
class DetoCommand(
    private val substituteRandomLettersByDsUseCase: SubstituteRandomCharactersByDsUseCase,
) : DiscordCommand {

    override fun accept(interaction: Interaction): InteractionResponse {
        val response = substituteRandomLettersByDsUseCase.execute(interaction.firstMessage)
        return InteractionResponse(response)
    }
}
