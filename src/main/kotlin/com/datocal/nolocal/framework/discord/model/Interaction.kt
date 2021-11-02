package com.datocal.nolocal.framework.discord.model

data class Interaction(
    val data: Data?,
    val type: Long,
)

data class Data(
    val name: String,
    val options: List<Option> = emptyList()
)

data class Option(
    val name: String,
    val value: String?,
)
