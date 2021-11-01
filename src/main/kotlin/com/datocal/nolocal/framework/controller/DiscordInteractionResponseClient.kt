package com.datocal.nolocal.framework.controller

import feign.Param
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    url = "https://discord.com/api/v8",
    name = "discordInteractionResponseClient"
)
interface DiscordInteractionResponseClient {

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/interactions/{interaction_id}/{interaction_token}/callback"]
    )
    fun respond(
        @RequestBody interaction: InteractionResponse?,
        @RequestParam("interaction_id") interactionId: String?,
        @RequestParam("interaction_token") interactionToken: String?,
    )

}

data class InteractionResponse(
    val type: Int,
    val data: InteractionResponseData?
)

data class InteractionResponseData(
    val tts: Boolean?,
    val content: String?,
    val embeds: List<Choice>,
    val allowed_mentions: AllowedMentions,
)

data class AllowedMentions (
    val parse: List<String>,
)

data class Choice(
    val name: String,
    val value: String,
)

