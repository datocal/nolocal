package com.datocal.nolocal.infrastructure.configuration.usecases

import com.datocal.nolocal.application.server.SetUpCredentialsUseCase
import com.datocal.nolocal.domain.server.CloudCredentialsRepository
import com.datocal.nolocal.domain.server.EncryptionService
import com.datocal.nolocal.infrastructure.repository.CloudCredentialsRedisRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import redis.clients.jedis.JedisPool

@Configuration
class CloudCredentialsConfiguration {

    @Bean
    fun setUpCredentialsUseCase(
        repository: CloudCredentialsRepository,
        encryptionService: EncryptionService,
    ): SetUpCredentialsUseCase {
        return SetUpCredentialsUseCase(repository, encryptionService)
    }

    @Bean
    fun cloudCredentialsRepository(jedisPool: JedisPool): CloudCredentialsRepository {
        return CloudCredentialsRedisRepository(jedisPool)
    }

    @Bean
    fun encryptionService(): EncryptionService {
        return EncryptionService()
    }
}
