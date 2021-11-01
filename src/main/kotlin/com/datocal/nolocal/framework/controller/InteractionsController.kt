package com.datocal.nolocal.framework.controller

import com.fasterxml.jackson.annotation.JsonCreator
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class InteractionsController() {

    private val logger = LoggerFactory.getLogger(InteractionsController::class.java)

    @PostMapping("/discord/interactions")
    fun execute(@RequestBody interaction: Interaction): Interaction {
        logger.info(interaction.toString())
        return interaction
    }
}

data class Interaction @JsonCreator constructor(
    val type: Int,
)
