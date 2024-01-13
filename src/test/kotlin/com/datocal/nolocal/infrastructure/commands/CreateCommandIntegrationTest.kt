package com.datocal.nolocal.infrastructure.commands

import com.datocal.nolocal.application.server.CreateServerUseCase
import com.datocal.nolocal.infrastructure.IntegrationTest
import com.datocal.nolocal.infrastructure.discord.client.DiscordWebhooksClientStubs
import io.restassured.http.ContentType
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.awaitility.Awaitility.await
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.timeout
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.Duration

class CreateCommandIntegrationTest : IntegrationTest() {
    @SpyBean
    private lateinit var createUseCase: CreateServerUseCase

    @Autowired
    private lateinit var stubs: DiscordWebhooksClientStubs

    private companion object {
        private const val INTERACTION_TOKEN = "dummy-test-token"
        private const val CREATE_REQUEST =
            """
                {
                    "token": "$INTERACTION_TOKEN",
                    "data": {
                        "name" : "create"
                    }
                }
            """
    }

    @Test
    fun `should respond with a followup`() {
        val expectations =
            stubs.`a followup returns 200`(
                INTERACTION_TOKEN,
                "Hey there! This is a followup to the command",
            )

        RestAssuredMockMvc.given()
            .contentType(ContentType.JSON)
            .body(CREATE_REQUEST)
            .`when`()
            .post(INTERACTIONS_ENDPOINT)
            .then()
            .assertThat(MockMvcResultMatchers.status().isOk)
            .body("type", Matchers.equalTo(4))
            .body("data.content", Matchers.equalTo("Working on it"))

        verify(createUseCase, timeout(5000).times(1)).execute(any())

        await()
            .atMost(Duration.ofSeconds(5))
            .untilAsserted { stubs.`a follow up message have been send`(expectations) }
    }
}
