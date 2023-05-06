package com.datocal.nolocal.infrastructure.repository

import com.datocal.nolocal.domain.account.Account
import com.datocal.nolocal.domain.server.CloudCredentials
import com.datocal.nolocal.domain.server.CloudCredentialsRepository
import com.datocal.nolocal.domain.server.CloudFlavor
import redis.clients.jedis.JedisPool

class CloudCredentialsRedisRepository(
    private val jedisPool: JedisPool,
) : CloudCredentialsRepository {
    override fun save(cloudCredentials: CloudCredentials) {
        jedisPool.resource.use { it.hset(cloudCredentials.redisKey(), "encryptedToken", cloudCredentials.encryptedToken) }
    }

    override fun get(owner: Account, flavor: CloudFlavor): CloudCredentials {
        val encryptedToken = jedisPool.resource.use { it.hget(owner.redisKey(flavor), "encryptedToken") }
        return CloudCredentials(owner, flavor, encryptedToken)
    }

    private fun CloudCredentials.redisKey() = owner.redisKey(flavor)

    private fun Account.redisKey(cloudFlavor: CloudFlavor) = "servers:tenant:$tenant:owner:$id:$cloudFlavor"
}
