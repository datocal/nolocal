package com.datocal.nolocal.infrastructure.discord.client

import com.datocal.nolocal.infrastructure.discord.configuration.DiscordApiConfigurationProperties
import com.datocal.nolocal.infrastructure.discord.model.ApplicationCommand
import org.mockserver.client.MockServerClient
import org.mockserver.matchers.Times
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.mockserver.model.JsonBody
import org.mockserver.model.MediaType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DiscordApiClientStubs {

    @Autowired
    private lateinit var discordProperties: DiscordApiConfigurationProperties

    @Autowired
    lateinit var mockServerClient: MockServerClient

    fun `a command return the command`(givenCommand: ApplicationCommand) {
        mockServerClient
            .`when`(
                HttpRequest.request()
                    .withPath("${discordProperties.endpoint}/commands")
                    .withMethod("POST")
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody(JsonBody(COMMANDS_POST_REQUEST.format(givenCommand.name, givenCommand.description, givenCommand.type))),
                Times.once(),
            )
            .respond(
                HttpResponse.response()
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody(COMMANDS_GET_RESPONSE_TEST_COMMAND),
            )
    }

    fun `commands return two commands`() {
        mockServerClient
            .`when`(
                HttpRequest.request()
                    .withPath("${discordProperties.endpoint}/commands")
                    .withMethod("GET"),
                Times.once(),
            )
            .respond(
                HttpResponse.response()
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody(COMMANDS_GET_RESPONSE_TWO_COMMANDS),
            )
    }

    fun `commands return no commands`() {
        mockServerClient
            .`when`(
                HttpRequest.request()
                    .withPath("${discordProperties.endpoint}/commands")
                    .withMethod("GET"),
                Times.once(),
            )
            .respond(
                HttpResponse.response()
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody(COMMANDS_GET_RESPONSE_EMPTY_COMMANDS),
            )
    }

    fun `commands return an empty body`() {
        mockServerClient
            .`when`(
                HttpRequest.request()
                    .withPath("${discordProperties.endpoint}/commands")
                    .withMethod("GET"),
                Times.once(),
            )
            .respond(
                HttpResponse.response()
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withStatusCode(204),
            )
    }

    fun `commands return an error`() {
        mockServerClient
            .`when`(
                HttpRequest.request()
                    .withPath("${discordProperties.endpoint}/commands")
                    .withMethod("GET"),
                Times.once(),
            )
            .respond(
                HttpResponse.response()
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withStatusCode(400),
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
