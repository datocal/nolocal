package com.datocal.nolocal.infrastructure.discord.client

import com.datocal.nolocal.infrastructure.IntegrationTest
import com.datocal.nolocal.infrastructure.discord.configuration.DiscordApiConfigurationProperties
import com.datocal.nolocal.infrastructure.discord.model.ApplicationCommand
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockserver.matchers.Times
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.HttpResponse.response
import org.mockserver.model.JsonBody
import org.mockserver.model.MediaType
import org.springframework.beans.factory.annotation.Autowired

internal class DiscordApiClientTest : IntegrationTest() {

    @Autowired
    private lateinit var client: DiscordApiClient

    @Autowired
    private lateinit var discordProperties: DiscordApiConfigurationProperties

    @Test
    fun `should get all application commands`() {
        `when requesting commands return two commands`()

        val commands = client.getCommands()

        assertEquals(2, commands.size)
        assertEquals(expectedCommands(), commands.toSet())
    }

    private fun expectedCommands() = setOf(
        ApplicationCommand(
            name = "culo",
            description = "El culo tuyo",
            type = 1
        ),
        ApplicationCommand(
            name = "roulette",
            description = "",
            type = 3
        ),
    )

    @Test
    fun `should get empty list of application commands`() {
        `when requesting commands return no commands`()

        val commands = client.getCommands()

        assertEquals(0, commands.size)
    }

    @Test
    fun `should register command`() {
        val givenCommand = ApplicationCommand(name = "test", type = 1, description = "Testing command")
        `when registering a command return the command`(givenCommand)

        client.register(givenCommand)
    }

    private fun `when registering a command return the command`(givenCommand: ApplicationCommand) {
        mockServerClient
            .`when`(
                request()
                    .withPath("${discordProperties.endpoint}/commands")
                    .withMethod("POST")
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody(JsonBody(COMMANDS_POST_REQUEST.format(givenCommand.name, givenCommand.description, givenCommand.type))),
                Times.once()
            )
            .respond(
                response()
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody(COMMANDS_GET_RESPONSE_TEST_COMMAND)
            )
    }

    fun `when requesting commands return two commands`() {
        mockServerClient
            .`when`(
                request()
                    .withPath("${discordProperties.endpoint}/commands")
                    .withMethod("GET"),
                Times.once()
            )
            .respond(
                response()
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody(COMMANDS_GET_RESPONSE_TWO_COMMANDS)
            )
    }

    fun `when requesting commands return no commands`() {
        mockServerClient
            .`when`(
                request()
                    .withPath("${discordProperties.endpoint}/commands")
                    .withMethod("GET"),
                Times.once()
            )
            .respond(
                response()
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody(COMMANDS_GET_RESPONSE_EMPTY_COMMANDS)
            )
    }

    private companion object {
        private const val COMMANDS_POST_REQUEST = """
            {
                "name": "%s",
                "description": "%s",
                "type": %d
            }
        """

        private const val COMMANDS_GET_RESPONSE_TWO_COMMANDS = """
            [
                {
                    "id": "124215325325261",
                    "application_id": "63215324324124",
                    "version": "53264367435235",
                    "default_permission": true,
                    "default_member_permissions": null,
                    "type": 1,
                    "name": "culo",
                    "description": "El culo tuyo",
                    "guild_id": "508685261190987776"
                },
                {
                    "id": "124215325325262",
                    "application_id": "63215324324124",
                    "version": "53264367435235",
                    "default_permission": true,
                    "default_member_permissions": null,
                    "type": 3,
                    "name": "roulette",
                    "description": "",
                    "guild_id": "508685261190987776"
                }
            ]
            
        """

        private const val COMMANDS_GET_RESPONSE_TEST_COMMAND = """
                {
                    "id": "124215325325262",
                    "application_id": "63215324324124",
                    "version": "53264367435235",
                    "default_permission": true,
                    "default_member_permissions": null,
                    "type": 1,
                    "name": "test",
                    "description": "Testing command",
                    "guild_id": "123213213123123"
                }
            
        """

        private const val COMMANDS_GET_RESPONSE_EMPTY_COMMANDS = "[]"
    }
}
