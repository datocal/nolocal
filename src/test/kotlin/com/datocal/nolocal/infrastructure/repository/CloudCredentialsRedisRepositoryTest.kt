package com.datocal.nolocal.infrastructure.repository

import com.datocal.nolocal.domain.account.Account
import com.datocal.nolocal.domain.account.Tenant
import com.datocal.nolocal.domain.server.CloudCredentials
import com.datocal.nolocal.domain.server.CloudFlavor
import com.datocal.nolocal.infrastructure.IntegrationTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import redis.clients.jedis.JedisPool

class CloudCredentialsRedisRepositoryTest : IntegrationTest() {
    @Autowired
    private lateinit var repository: CloudCredentialsRedisRepository

    @Autowired
    private lateinit var jedisPool: JedisPool

    @Test
    fun `should save credentials on redis`() {
        val given = `expected credentials`()

        repository.save(given)

        Assertions.assertEquals("123", savedItem())
    }

    private fun savedItem(): String {
        return jedisPool.resource.use { it.hget("servers:tenant:${Tenant.DISCORD}:owner:Example#1234:digitalocean", "encryptedToken") }
    }

    @Test
    fun `should get saved credentials from redis`() {
        val given = `an account owner`()
        `given some stored credentials`()

        val credentials = repository.get(given, CloudFlavor.DIGITAL_OCEAN)

        Assertions.assertEquals(`expected credentials`(), credentials)
    }

    private fun `given some stored credentials`() {
        return jedisPool.resource.use {
            it.hset(
                "servers:tenant:${Tenant.DISCORD}:owner:Example#1234:digitalocean",
                "encryptedToken",
                "123",
            )
        }
    }

    private fun `expected credentials`(): CloudCredentials {
        return CloudCredentials(
            owner = `an account owner`(),
            flavor = CloudFlavor.DIGITAL_OCEAN,
            encryptedToken = "123",
        )
    }

    private fun `an account owner`(): Account {
        return Account(
            id = "Example#1234",
            tenant = Tenant.DISCORD,
        )
    }
}
