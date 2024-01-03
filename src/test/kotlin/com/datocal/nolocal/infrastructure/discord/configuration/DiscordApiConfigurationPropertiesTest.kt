package com.datocal.nolocal.infrastructure.discord.configuration

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DiscordApiConfigurationPropertiesTest {

    private companion object {
        private const val HOST = "https://discord.com"
        private const val BASE_ENDPOINT = "/api/v10"
        private const val APPLICATIONS_BASE_ENDPOINT = "/applications"
        private const val WEBHOOKS_BASE_ENDPOINT = "/webhooks"
        private const val EXPECTED_WEBHOOKS_DISCORD_URL = "https://discord.com/api/v10/webhooks/{client-id}"
        private const val EXPECTED_APPLICATIONS_DISCORD_URL = "https://discord.com/api/v10/applications/{client-id}"
        private const val EXPECTED_APPLICATIONS_DISCORD_GUILD_URL = "https://discord.com/api/v10/applications/{client-id}/guilds/{guild-id}"
    }

    @Test
    fun `should create url with guild mode`() {
        val configurationProperties = `given a configuration properties with guild mode by`(guildMode = true)

        assertEquals(EXPECTED_APPLICATIONS_DISCORD_GUILD_URL, configurationProperties.fullApplicationsUrl)
    }

    @Test
    fun `should create url without guild mode`() {
        val configurationProperties = `given a configuration properties with guild mode by`(guildMode = false)

        assertEquals(EXPECTED_APPLICATIONS_DISCORD_URL, configurationProperties.fullApplicationsUrl)
    }

    private fun `given a configuration properties with guild mode by`(guildMode: Boolean): DiscordApiConfigurationProperties {
        val configurationProperties = DiscordApiConfigurationProperties()
        configurationProperties.guildMode = guildMode
        configurationProperties.host = HOST
        configurationProperties.baseEndpoint = BASE_ENDPOINT
        configurationProperties.applicationsBaseEndpoint = APPLICATIONS_BASE_ENDPOINT
        configurationProperties.webhooksBaseEndpoint = WEBHOOKS_BASE_ENDPOINT
        return configurationProperties
    }

    @Test
    fun `should create a webhooks url`() {
        val configurationProperties = `given a configuration properties with guild mode by`(guildMode = false)

        assertEquals(EXPECTED_WEBHOOKS_DISCORD_URL, configurationProperties.fullWebHooksUrl)
    }
}
