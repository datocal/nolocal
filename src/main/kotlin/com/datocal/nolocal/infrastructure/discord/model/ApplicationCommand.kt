package com.datocal.nolocal.infrastructure.discord.model

data class ApplicationCommand(
    val type: Int,
    val name: String,
    val description: String,
)
