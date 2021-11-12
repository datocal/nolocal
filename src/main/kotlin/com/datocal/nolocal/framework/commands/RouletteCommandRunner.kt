package com.datocal.nolocal.framework.commands

import com.datocal.nolocal.domain.MessageResolver
import com.datocal.nolocal.framework.discord.model.Interaction
import com.datocal.nolocal.framework.discord.model.InteractionResponse
import com.datocal.nolocal.framework.discord.model.InteractionResponseData
import com.datocal.nolocal.usecases.roulette.GetRandomItemUseCase
import com.datocal.nolocal.usecases.roulette.GetRandomItemUseCaseRequest
import com.datocal.nolocal.usecases.roulette.GetRandomItemUseCaseResponse
import org.springframework.stereotype.Component

@Component("roulette")
class RouletteCommandRunner(
    private val getRandomItemUseCase: GetRandomItemUseCase,
    private val messageResolver: MessageResolver,
) : DiscordCommandRunner {

    override fun accept(interaction: Interaction): InteractionResponse {
        val request = buildRequest(interaction)
        return buildResponse(getRandomItemUseCase.execute(request))
    }

    private fun buildResponse(response: GetRandomItemUseCaseResponse): InteractionResponse {
        return InteractionResponse(
            data = InteractionResponseData(
                content = response.item ?: messageResolver.get("roulette.not_found"),
            )
        )
    }

    private fun buildRequest(interaction: Interaction): GetRandomItemUseCaseRequest {
        val items = (
            interaction.data
                ?.resolved
                ?.messages
                ?: emptyMap()
            ).entries
            .mapNotNull { it.value?.content }
            .map { it.lines() }
            .flatten()
            .map { it.trim() }
            .filter { it.isNotEmpty() }
        return GetRandomItemUseCaseRequest(items)
    }
}
