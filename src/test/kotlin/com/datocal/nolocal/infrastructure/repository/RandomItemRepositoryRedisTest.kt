package com.datocal.nolocal.infrastructure.repository

import com.datocal.nolocal.domain.roulette.RandomItemRepository
import com.datocal.nolocal.infrastructure.IntegrationTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import redis.clients.jedis.JedisPool
import java.util.UUID

internal class RandomItemRepositoryRedisTest : IntegrationTest() {

    @Autowired
    private lateinit var repositoryRedis: RandomItemRepository

    @Autowired
    private lateinit var jedisPool: JedisPool

    @Test
    fun `should save item in redis`() {
        val itemToSave = UUID.randomUUID().toString()

        repositoryRedis.save(itemToSave)

        assertEquals(itemToSave, savedItem())
    }

    private fun savedItem(): String {
        return jedisPool.resource.use { it.lpop(RandomItemRepositoryRedis.COMMAND_ROULETTE_RANDOMS_KEY) }
    }
}
