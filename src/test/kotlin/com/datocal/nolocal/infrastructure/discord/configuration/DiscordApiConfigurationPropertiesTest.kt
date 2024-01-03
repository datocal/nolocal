package com.datocal.nolocal.infrastructure.discord.configuration

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DiscordApiConfigurationPropertiesTest {

    private companion object {
        private const val HOST = "https://discord.com"
        private const val BASE_ENDPOINT = "/api/v10/applications"
        private const val EXPECTED_DISCORD_URL = "$HOST$BASE_ENDPOINT/{client-id}"
        private const val EXPECTED_DISCORD_GUILD_URL = "$EXPECTED_DISCORD_URL/guilds/{guild-id}"
    }

    @Test
    fun `should create url with guild mode`() {
        val configurationProperties = `given a configuration properties with guild mode by`()

        assertEquals(EXPECTED_DISCORD_GUILD_URL, configurationProperties.fullBaseUrlWithPlaceholders)
    }

    @Test
    fun `should create url without guild mode`() {
        val configurationProperties = `given a configuration properties with guild mode by`(guildMode = false)

        assertEquals(EXPECTED_DISCORD_URL, configurationProperties.fullBaseUrlWithPlaceholders)
    }

    private fun `given a configuration properties with guild mode by`(guildMode: Boolean = true): DiscordApiConfigurationProperties {
        val configurationProperties = DiscordApiConfigurationProperties()
        configurationProperties.guildMode = guildMode
        configurationProperties.host = HOST
        configurationProperties.baseEndpoint = BASE_ENDPOINT
        return configurationProperties
    }
}
