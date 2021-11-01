package com.datocal.nolocal.framework.controller

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class InteractionsController(
    private val objectMapper: ObjectMapper,
) {

    private val logger = LoggerFactory.getLogger(InteractionsController::class.java)

    @PostMapping("/discord/interactions")
    fun execute(@RequestBody interaction: String): Interaction {
        val it = objectMapper.readValue(interaction, Interaction::class.java)
        logger.info(interaction)
        return it
    }
}

data class Interaction @JsonCreator constructor(
    val type: Int,
)
