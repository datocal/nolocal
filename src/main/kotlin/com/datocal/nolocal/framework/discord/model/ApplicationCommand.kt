package com.datocal.nolocal.framework.discord.model

data class ApplicationCommand(
    val type: Int,
    val name: String,
    val description: String,
)
