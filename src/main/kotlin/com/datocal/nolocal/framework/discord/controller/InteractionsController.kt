package com.datocal.nolocal.framework.discord.controller

import com.datocal.nolocal.framework.commands.DiscordCommandRunner
import com.datocal.nolocal.framework.discord.model.Interaction
import com.datocal.nolocal.framework.discord.model.InteractionResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class InteractionsController(
    private val commands: Map<String, DiscordCommandRunner>
) {

    @PostMapping("/discord/interactions")
    fun execute(@RequestBody interaction: Interaction): InteractionResponse {
        return commands[interaction.data?.name]?.accept(interaction) ?: defaultResponse()
    }

    // ACK Discord PING checks
    private fun defaultResponse() = InteractionResponse(type = 1, data = null)
}
