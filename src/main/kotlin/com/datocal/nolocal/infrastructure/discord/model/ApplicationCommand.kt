package com.datocal.nolocal.infrastructure.discord.model

data class ApplicationCommand(
    val name: String,
    val type: Int,
    val description: String,
    val options: List<ApplicationCommandOption> ? = null,
)

data class ApplicationCommandOption(
    val name: String,
    val description: String,
    val type: Int = 3,
    val required: Boolean = true,
    val choices: List<ApplicationCommandOptionChoice>? = null,
)

data class ApplicationCommandOptionChoice(
    val name: String,
    val value: String,
)
