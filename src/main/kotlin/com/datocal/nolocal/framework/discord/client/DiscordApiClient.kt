package com.datocal.nolocal.framework.discord.client

import com.datocal.nolocal.framework.discord.model.InteractionResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    url = "https://discord.com/api/v8",
    name = "discordApiClient",
)
interface DiscordApiClient {

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/interactions/{interaction_id}/{interaction_token}/callback"],
    )
    fun respond(
        @RequestBody interaction: InteractionResponse?,
        @RequestParam("interaction_id") interactionId: String?,
        @RequestParam("interaction_token") interactionToken: String?,
    )
}
