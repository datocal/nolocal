package com.datocal.nolocal.infrastructure.configuration.usecases

import com.datocal.nolocal.application.RandomProvider
import com.datocal.nolocal.application.SubstituteRandomCharactersByDsUseCase
import com.datocal.nolocal.application.roulette.GetRandomItemUseCase
import com.datocal.nolocal.domain.MessageResolver
import com.datocal.nolocal.domain.dummy.Ping
import com.datocal.nolocal.domain.roulette.RandomItemRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfiguration {
    @Bean
    fun getRandomItemUseCase(repository: RandomItemRepository): GetRandomItemUseCase {
        return GetRandomItemUseCase(repository)
    }

    @Bean
    fun ping(messages: MessageResolver): Ping {
        return Ping(messages)
    }

    @Bean
    fun substituteRandomLettersByDsUseCase(): SubstituteRandomCharactersByDsUseCase {
        return SubstituteRandomCharactersByDsUseCase(randomProvider())
    }

    @Bean
    fun randomProvider(): RandomProvider {
        return RandomProvider()
    }
}
