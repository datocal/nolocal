package com.datocal.nolocal.infrastructure.configuration

import com.datocal.nolocal.application.server.CreateServerUseCase
import com.datocal.nolocal.infrastructure.discord.client.DiscordWebhooksClient
import com.datocal.nolocal.infrastructure.messaging.server.CreateCommandListener
import com.datocal.nolocal.infrastructure.messaging.server.CreateCommandPublisher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ApplicationEventMulticaster
import org.springframework.context.event.SimpleApplicationEventMulticaster
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@Configuration
class EventConfiguration {
    @Bean("createCommandPublisher")
    fun createCommandPublisher(applicationEventPublisher: ApplicationEventPublisher): CreateCommandPublisher {
        return CreateCommandPublisher(applicationEventPublisher)
    }

    @Bean
    fun createCommandListener(
        createServerUseCase: CreateServerUseCase,
        discordWebhooksClient: DiscordWebhooksClient,
    ): CreateCommandListener {
        return CreateCommandListener(createServerUseCase, discordWebhooksClient)
    }

    @Bean("applicationEventMulticaster")
    fun simpleApplicationEventMulticaster(
        @Qualifier("eventExecutor") taskExecutor: ThreadPoolTaskExecutor,
    ): ApplicationEventMulticaster {
        val eventMulticaster = SimpleApplicationEventMulticaster()
        eventMulticaster.setTaskExecutor(taskExecutor)
        return eventMulticaster
    }

    @Bean("eventExecutor")
    fun taskExecutor(): ThreadPoolTaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 10
        executor.queueCapacity = 40
        executor.setPrestartAllCoreThreads(false)
        return executor
    }
}
