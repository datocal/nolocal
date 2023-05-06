package com.datocal.nolocal.infrastructure.discord.model

open class OptionProvider {

    open fun options(): List<ApplicationCommandOption>? {
        return null
    }
}
