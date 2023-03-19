package com.datocal.nolocal.infrastructure.discord.controller

import com.datocal.nolocal.infrastructure.commands.DiscordCommand
import com.datocal.nolocal.infrastructure.discord.model.Interaction
import com.datocal.nolocal.infrastructure.discord.model.InteractionResponse
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class InteractionsController(
    private val commands: Map<String, DiscordCommand>,
) {
    private val logger = LoggerFactory.getLogger(InteractionsController::class.java)

    @PostMapping("/discord/interactions")
    fun execute(@RequestBody interaction: Interaction): InteractionResponse {
        logger.trace("Incoming request: $interaction")
        val response = commands[interaction.command]?.accept(interaction) ?: defaultResponse()
        logger.trace("Response: $response")
        return response
    }

    // ACK Discord PING checks
    private fun defaultResponse() = InteractionResponse()
}
