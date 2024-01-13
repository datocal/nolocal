package com.datocal.nolocal.infrastructure.discord.model

data class Interaction(
    val id: String = "1",
    val token: String = "dummy-token",
    val data: Data?,
    val type: Long,
) {
    val command: String?
        get() = data?.name

    val textMessages: List<String>
        get() {
            return (
                data
                    ?.resolved
                    ?.messages
            )?.entries
                ?.mapNotNull { it.value?.content }
                ?.toList() ?: emptyList()
        }

    val firstMessage: String
        get() = textMessages.firstOrNull() ?: ""
}

data class Data(
    val name: String,
    val resolved: Resolved? = null,
)

data class Resolved(
    val messages: Map<String, PartialMessage?> = emptyMap(),
)

data class PartialMessage(
    val content: String? = "",
)
