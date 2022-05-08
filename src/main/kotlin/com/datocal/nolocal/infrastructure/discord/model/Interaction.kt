package com.datocal.nolocal.infrastructure.discord.model

data class Interaction(
    val data: Data?,
    val type: Long,
) {
    val command: String?
        get() = data?.name
}

data class Data(
    val name: String,
    val resolved: Resolved? = null,
)

data class Resolved(
    val messages: Map<String, PartialMessage?> = emptyMap()
)

data class PartialMessage(
    val content: String? = "",
)
