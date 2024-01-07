package com.datocal.nolocal.infrastructure.messaging

import com.datocal.nolocal.infrastructure.discord.model.Interaction

interface CommandPublisher<T> {
    fun publish(interaction: Interaction)
}
