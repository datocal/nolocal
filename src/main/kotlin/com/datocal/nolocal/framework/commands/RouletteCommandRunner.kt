package com.datocal.nolocal.framework.commands

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
) : DiscordCommandRunner {

    override fun accept(interaction: Interaction): InteractionResponse {
        val request = buildRequest(interaction)
        return buildResponse(getRandomItemUseCase.execute(request))
    }

    private fun buildResponse(response: GetRandomItemUseCaseResponse): InteractionResponse {
        return InteractionResponse(
            type = 2,
            data = InteractionResponseData(
                content = response.item ?: "No items found",
            )
        )
    }

    private fun buildRequest(interaction: Interaction): GetRandomItemUseCaseRequest {
        val items = interaction.data?.options?.mapNotNull { it.value?.trim() } ?: emptyList()
        return GetRandomItemUseCaseRequest(items)
    }
}
