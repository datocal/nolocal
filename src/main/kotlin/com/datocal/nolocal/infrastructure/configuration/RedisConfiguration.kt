package com.datocal.nolocal.infrastructure.configuration

import com.datocal.nolocal.infrastructure.repository.RandomItemRepositoryRedis
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import redis.clients.jedis.JedisPool

@Configuration
class RedisConfiguration {

    @Value("\${redis.host}")
    private lateinit var redisHost: String

    @Value("\${redis.port}")
    private var redisPort: Int = 0

    @Bean
    fun redisClient(): JedisPool {
        return JedisPool(redisHost, redisPort)
    }

    @Bean
    fun randomItemRepositoryRedis(redisClient: JedisPool): RandomItemRepositoryRedis {
        return RandomItemRepositoryRedis(redisClient)
    }
}
