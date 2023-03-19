package com.datocal.nolocal.infrastructure.discord.model

data class InteractionResponse private constructor(
    val type: Int,
    val data: InteractionResponseData?,
) {
    companion object {
        private const val TYPE_ACK: Int = 1
        private const val TYPE_TEXT: Int = 4

        operator fun invoke(message: String) =
            InteractionResponse(
                type = TYPE_TEXT,
                data = InteractionResponseData(
                    content = message,
                ),
            )

        operator fun invoke() =
            InteractionResponse(
                type = TYPE_ACK,
                data = null,
            )
    }
}

data class InteractionResponseData(
    val content: String?,
)
