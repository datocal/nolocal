package com.datocal.nolocal.infrastructure.commands

import com.datocal.nolocal.domain.MessageResolver
import com.datocal.nolocal.infrastructure.discord.model.Command
import com.datocal.nolocal.infrastructure.discord.model.Interaction
import com.datocal.nolocal.infrastructure.discord.model.InteractionResponse
import com.datocal.nolocal.usecases.roulette.GetRandomItemUseCase
import com.datocal.nolocal.usecases.roulette.GetRandomItemUseCaseRequest
import com.datocal.nolocal.usecases.roulette.GetRandomItemUseCaseResponse
import org.springframework.stereotype.Component

@Component("roulette")
@Command(
    command = "roulette",
    type = Command.TYPE_MESSAGE,
)
class RouletteCommand(
    private val getRandomItemUseCase: GetRandomItemUseCase,
    private val messageResolver: MessageResolver,
) : DiscordCommand {

    override fun accept(interaction: Interaction): InteractionResponse {
        val request = buildRequest(interaction)
        val response = getRandomItemUseCase.execute(request)
        return InteractionResponse(getMessage(response))
    }

    private fun getMessage(response: GetRandomItemUseCaseResponse): String {
        return response.item ?: messageResolver.get("roulette.not_found")
    }

    private fun buildRequest(interaction: Interaction): GetRandomItemUseCaseRequest {
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
        return GetRandomItemUseCaseRequest(items)
    }
}
