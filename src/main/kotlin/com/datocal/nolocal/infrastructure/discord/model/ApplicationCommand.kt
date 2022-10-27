package com.datocal.nolocal.infrastructure.discord.model

data class ApplicationCommand(
    val name: String,
    val type: Int,
    val description: String,
)
