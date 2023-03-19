package com.datocal.nolocal.infrastructure.repository

import com.datocal.nolocal.domain.roulette.RandomItemRepository
import redis.clients.jedis.JedisPool

class RandomItemRepositoryRedis(
    private val jedisPool: JedisPool,
) : RandomItemRepository {

    override fun save(randomItem: String) {
        jedisPool.resource.use { it.lpush(COMMAND_ROULETTE_RANDOMS_KEY, randomItem) }
    }

    companion object {
        const val COMMAND_ROULETTE_RANDOMS_KEY = "command:roulette:randoms"
    }
}
