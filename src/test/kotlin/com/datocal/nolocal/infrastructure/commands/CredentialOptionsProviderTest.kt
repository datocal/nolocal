package com.datocal.nolocal.infrastructure.commands

import com.datocal.nolocal.infrastructure.discord.model.ApplicationCommandOption
import com.datocal.nolocal.infrastructure.discord.model.ApplicationCommandOptionChoice
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CredentialOptionsProviderTest {

    private val credentialOptionsProvider = CredentialOptionsProvider()

    @Test
    fun `should have the appropiate configuration`() {
        val options = credentialOptionsProvider.options()

        Assertions.assertEquals(expectedOptions(), options)
    }

    private fun expectedOptions(): List<ApplicationCommandOption> {
        return listOf(
            ApplicationCommandOption(
                name = "provider",
                description = "The cloud provider to store the token",
                type = 3,
                required = true,
                choices = listOf(
                    ApplicationCommandOptionChoice(
                        name = "Digital Ocean",
                        value = "digital_ocean",
                    ),
                ),
            ),
            ApplicationCommandOption(
                name = "token",
                description = "The token to store",
                type = 3,
                required = true,
            ),
            ApplicationCommandOption(
                name = "password",
                description = "password used to access this resource",
                type = 3,
                required = true,
            ),
        )
    }
}
