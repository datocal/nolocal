package com.datocal.nolocal.framework.controller


import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class InteractionsController() {

    @PostMapping("/discord/interactions")
    fun execute(@RequestBody interaction: Interaction): Interaction {
        return interaction
    }
}

data class Interaction @JsonCreator constructor(
    val type: Int,
)
