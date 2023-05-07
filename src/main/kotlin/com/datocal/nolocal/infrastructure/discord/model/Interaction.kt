package com.datocal.nolocal.infrastructure.discord.model

data class Interaction(
    val data: Data?,
    val type: Long,
    val member: GuildMember?,
    val user: User?
) {
    fun findOption(name: String, required : Boolean = true): String? {
        val value = data?.options?.find { it.name == name }?.value
        if(required && value == null){
            throw RequiredOptionException(name)
        }
        return value
    }

    fun getUserId() : String {
        return member?.user?.id ?: user?.id!!
    }

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

data class GuildMember(
    val user: User,
)

data class User (
    val id: String,
)

class RequiredOptionException(val option: String) : Exception()

data class Data(
    val name: String,
    val resolved: Resolved? = null,
    val options: List<InteractionDataOption>? = null,
)

data class InteractionDataOption(
    val name: String,
    val value: String,
)

data class Resolved(
    val messages: Map<String, PartialMessage?> = emptyMap(),
)

data class PartialMessage(
    val content: String? = "",
)
