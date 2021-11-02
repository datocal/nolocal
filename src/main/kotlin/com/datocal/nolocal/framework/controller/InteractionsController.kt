package com.datocal.nolocal.framework.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class InteractionsController(
    private val apiClient: DiscordApiClient,
) {

    private val logger = LoggerFactory.getLogger(InteractionsController::class.java)

    @PostMapping("/discord/interactions")
    fun execute(@RequestBody interaction: Interaction): Interaction {
        logger.info(interaction.toString())
        apiClient.respond(defaultResponse(), interaction.id, interaction.token)
        return interaction
    }

    private fun defaultResponse() = InteractionResponse(
        type = 4,
        data = InteractionResponseData(
            tts = false,
            content = "El culo tuyo",
            embeds = emptyList(),
            allowed_mentions = AllowedMentions(
                parse = emptyList()
            )
        )
    )
}

data class Interaction(
    val application_id: String,
    val channel_id: String?,
    val data: Data?,
    val guild_id: String?,
    val id: String,
    val member: Member?,
    val user: User?,
    val token: String,
    val type: Long,
    val version: Long
)

data class Data(
    val id: String,
    val name: String,
    val type: Long
)

data class Member(
    val avatar: String?,
    val deaf: Boolean,
    val is_pending: Boolean?,
    val joined_at: String,
    val mute: Boolean,
    val nick: String?,
    val pending: Boolean?,
    val permissions: String?,
    val premium_since: String?,
    val roles: List<String>,
    val user: User?
)

data class User(
    val avatar: String?,
    val discriminator: String,
    val id: String,
    val public_flags: Long?,
    val username: String
)
