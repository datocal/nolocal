package com.datocal.nolocal.framework.discord.model

data class InteractionResponse(
    val type: Int = 4,
    val data: InteractionResponseData?
)

data class InteractionResponseData(
    val content: String?,
)
