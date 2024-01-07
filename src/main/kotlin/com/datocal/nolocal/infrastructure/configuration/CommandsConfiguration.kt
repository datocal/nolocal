package com.datocal.nolocal.infrastructure.configuration

import com.datocal.nolocal.infrastructure.commands.Commands
import com.datocal.nolocal.infrastructure.commands.CreateCommand
import com.datocal.nolocal.infrastructure.messaging.server.CreateCommandPublisher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CommandsConfiguration {
    @Bean(Commands.CREATE)
    fun createCommand(
        @Qualifier("createCommandPublisher") createCommandPublisher: CreateCommandPublisher,
    ): CreateCommand {
        return CreateCommand(createCommandPublisher)
    }
}
