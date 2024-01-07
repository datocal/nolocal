package com.datocal.nolocal.infrastructure.configuration

import com.datocal.nolocal.infrastructure.messaging.server.CreateCommandPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EventConfiguration {
    @Bean("createCommandPublisher")
    fun createCommandPublisher(applicationEventPublisher: ApplicationEventPublisher): CreateCommandPublisher {
        return CreateCommandPublisher(applicationEventPublisher)
    }
}
