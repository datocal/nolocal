package com.datocal.nolocal.infrastructure.discord.configuration

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DiscordApiConfigurationPropertiesTest {

    private companion object {
        private const val EXPECTED_DISCORD_DEV_URL =
            "https://discord.com/api/v8/applications/{client-id}/guilds/{guild-id}"
    }

    @Test
    fun `should create url for dev environment`() {
        val configurationProperties = `given a configuration properties of the dev environment`()

        assertEquals(EXPECTED_DISCORD_DEV_URL, configurationProperties.fullBaseUrlWithPlaceholders)
    }

    private fun `given a configuration properties of the dev environment`(): DiscordApiConfigurationProperties {
        val configurationProperties = DiscordApiConfigurationProperties()
        configurationProperties.activeProfile = "dev"
        configurationProperties.host = "https://discord.com"
        configurationProperties.baseEndpoint = "/api/v8/applications"
        return configurationProperties
    }
}
