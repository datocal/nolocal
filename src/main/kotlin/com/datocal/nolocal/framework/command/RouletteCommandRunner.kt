package com.datocal.nolocal.framework.command

import com.datocal.nolocal.framework.removeCommandFromRawMessage
import com.datocal.nolocal.usecases.roulette.GetRandomItemUseCase
import com.datocal.nolocal.usecases.roulette.GetRandomItemUseCaseRequest
import com.datocal.nolocal.usecases.roulette.GetRandomItemUseCaseResponse
import org.javacord.api.event.message.MessageCreateEvent
import org.springframework.stereotype.Component

@Component("roulette")
class RouletteCommandRunner(
    private val getRandomItemUseCase: GetRandomItemUseCase,
) : DiscordCommandRunner {

    override fun accept(event: MessageCreateEvent) {
        val request = buildRequest(event.messageContent)
        val response = buildResponse(getRandomItemUseCase.execute(request))
        event.channel.sendMessage(response)
    }

    private fun buildResponse(response: GetRandomItemUseCaseResponse): String {
        return response.item ?: "No items found"
    }

    private fun buildRequest(message: String): GetRandomItemUseCaseRequest {
        val items = removeCommandFromRawMessage(message).lines().map { it.trim() }
        return GetRandomItemUseCaseRequest(items = items)
    }
}
