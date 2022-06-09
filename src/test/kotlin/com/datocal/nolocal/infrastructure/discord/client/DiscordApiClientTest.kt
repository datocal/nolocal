package com.datocal.nolocal.infrastructure.discord.client

import com.datocal.nolocal.infrastructure.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class DiscordApiClientTest : IntegrationTest() {

    @Autowired
    private lateinit var client: DiscordApiClient

    @Test
    fun `should get all application commands`() {
    }

    @Test
    fun `should get empty list of application commands`() {
    }

    @Test
    fun `should register command`() {
    }
}
