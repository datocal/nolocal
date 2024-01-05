package com.datocal.nolocal.infrastructure.discord.client

import com.datocal.nolocal.infrastructure.discord.configuration.DiscordApiConfigurationProperties
import org.mockserver.client.MockServerClient
import org.mockserver.matchers.Times
import org.mockserver.mock.Expectation
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.mockserver.model.HttpStatusCode
import org.mockserver.model.JsonBody
import org.mockserver.model.MediaType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@Suppress("ktlint:standard:function-naming")
class DiscordWebhooksClientStubs {
    @Autowired
    private lateinit var discordProperties: DiscordApiConfigurationProperties

    @Autowired
    lateinit var mockServer: MockServerClient

    fun `a followup returns 200`(
        interactionToken: String,
        content: String,
    ): Array<Expectation> {
        return mockServer
            .`when`(
                HttpRequest.request()
                    .withPath("${discordProperties.webhooksEndpoint}/$interactionToken")
                    .withMethod("POST")
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody(JsonBody(FOLLOWUP_POST_REQUEST.format(content))),
                Times.once(),
            )
            .respond(
                HttpResponse.response()
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withStatusCode(HttpStatusCode.OK_200.code()),
            )
    }

    fun `a follow up message have been send`(expectations: Array<Expectation>)  {
        expectations.forEach { mockServer.verify(it.id) }
    }

    private companion object {
        private const val FOLLOWUP_POST_REQUEST = """
            {
                "content": "%s"
            }
        """
    }
}
