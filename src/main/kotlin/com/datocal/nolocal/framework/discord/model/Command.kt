package com.datocal.nolocal.framework.discord.model

annotation class Command(
    val command: String,
    val description: String,
    val type: Int = TYPE_CHAT_INPUT,
) {
    companion object {
        const val TYPE_CHAT_INPUT: Int = 1
        const val TYPE_USER: Int = 2
        const val TYPE_MESSAGE: Int = 3
    }
}
