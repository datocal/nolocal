package com.datocal.nolocal.framework.discord.model

data class Interaction(
    val data: Data?,
    val type: Long,
)

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
