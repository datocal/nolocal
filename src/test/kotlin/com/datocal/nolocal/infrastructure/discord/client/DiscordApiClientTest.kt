package com.datocal.nolocal.infrastructure.discord.client

import com.datocal.nolocal.infrastructure.IntegrationTest
import com.datocal.nolocal.infrastructure.discord.model.ApplicationCommand
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.reactive.function.client.WebClientResponseException

internal class DiscordApiClientTest : IntegrationTest() {
    @Autowired
    private lateinit var client: DiscordApplicationsClient

    @Autowired
    private lateinit var given: DiscordApplicationClientStubs

    @Test
    fun `should get all application commands`() {
        given.`commands return two commands`()

        val commands = client.getCommands()

        assertEquals(2, commands.size)
        assertEquals(expectedCommands(), commands.toSet())
    }

    private fun expectedCommands() =
        setOf(
            ApplicationCommand(
                name = "culo",
                description = "El culo tuyo",
                type = 1,
            ),
            ApplicationCommand(
                name = "roulette",
                description = "",
                type = 3,
            ),
        )

    @Test
    fun `should get empty list of application commands`() {
        given.`commands return no commands`()

        val commands = client.getCommands()

        assertEquals(0, commands.size)
    }

    @Test
    fun `should get empty list of application commands when no body`() {
        given.`commands return an empty body`()

        val commands = client.getCommands()

        assertEquals(0, commands.size)
    }

    @Test
    fun `should throw exception on response error`() {
        given.`commands return an error`()

        assertThrows(WebClientResponseException::class.java) { client.getCommands() }
    }

    @Test
    fun `should register command`() {
        val givenCommand = ApplicationCommand(name = "test", type = 1, description = "Testing command")
        given.`a command return the command`(givenCommand)

        client.register(givenCommand)
    }
}
