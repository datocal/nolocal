package com.datocal.nolocal.infrastructure.configuration.usecases

import com.datocal.nolocal.domain.MessageResolver
import com.datocal.nolocal.domain.dummy.Ping
import com.datocal.nolocal.usecases.roulette.GetRandomItemUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfiguration {

    @Bean
    fun getRandomItemUseCase(): GetRandomItemUseCase {
        return GetRandomItemUseCase()
    }

    @Bean
    fun ping(messages: MessageResolver): Ping {
        return Ping(messages)
    }
}
