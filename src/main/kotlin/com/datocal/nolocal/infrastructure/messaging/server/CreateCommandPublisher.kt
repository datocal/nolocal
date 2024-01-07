package com.datocal.nolocal.infrastructure.messaging.server

import com.datocal.nolocal.infrastructure.discord.model.Interaction
import com.datocal.nolocal.infrastructure.messaging.CommandPublisher
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationEventPublisher

data class CreateRequest(val eventSource: Any, val token: String) : ApplicationEvent(eventSource)

class CreateCommandPublisher(private val eventPublisher: ApplicationEventPublisher) : CommandPublisher<CreateRequest> {
    override fun publish(interaction: Interaction) {
        eventPublisher.publishEvent(interaction.toEvent(this))
    }

    private fun Interaction.toEvent(source: Any): ApplicationEvent {
        return CreateRequest(source, token)
    }
}
