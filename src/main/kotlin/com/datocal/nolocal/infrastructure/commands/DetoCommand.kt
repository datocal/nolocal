package com.datocal.nolocal.infrastructure.commands

import com.datocal.nolocal.infrastructure.discord.model.Command
import com.datocal.nolocal.infrastructure.discord.model.Interaction
import com.datocal.nolocal.infrastructure.discord.model.InteractionResponse
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component("deto")
@Command(
    command = "deto",
    type = Command.TYPE_MESSAGE,
)
class DetoCommand() : DiscordCommand {

    override fun accept(interaction: Interaction): InteractionResponse {
        val request = buildRequest(interaction)
        val response = replaceByD(request)
        return InteractionResponse(response)
    }

    private fun replaceByD(request: String): String {
        val size = request.length
        val lettersToReplace = (Random.nextInt(size - 1) * 0.1).toInt()
        var newString = request
        for (i in 0..lettersToReplace) {
            val letterToReplace = request[Random.nextInt(size - 1)]
            if (!listOf('a', 'e', 'i', 'o', 'u', ' ').contains(letterToReplace)) {
                newString = newString.replace(letterToReplace, 'd')
            }
        }
        return newString
    }

    private fun buildRequest(interaction: Interaction): String {
        val items = (
            interaction.data
                ?.resolved
                ?.messages
                ?: emptyMap()
            ).entries
            .asSequence()
            .mapNotNull { it.value?.content }
            .map { it.lines() }
            .flatten()
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .toList()
            .first()
        return items
    }
}
