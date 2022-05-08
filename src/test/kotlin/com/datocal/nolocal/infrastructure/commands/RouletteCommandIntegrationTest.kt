package com.datocal.nolocal.infrastructure.commands

import com.datocal.nolocal.domain.MessageResolver
import com.datocal.nolocal.infrastructure.controller.IntegrationTest
import io.restassured.http.ContentType
import io.restassured.module.mockmvc.RestAssuredMockMvc
import io.restassured.path.json.JsonPath
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class RouletteCommandIntegrationTest : IntegrationTest() {
    private companion object {

        private const val ROULETTE_REQUEST =
            """
                {
                    "data": {
                        "name" : "roulette",
                        "resolved": {
                            "messages": {
                                "867793854505943041": {
                                    "content": "%s"
                                }
                            }
                        }
                    },
                    "type" : 2
                }
            """

        private const val ROULETTE_WITHOUT_MESSAGE_REQUEST =
            """
                {
                    "data": {
                        "name" : "roulette"
                    },
                    "type" : 2
                }
            """
        private const val NO_ITEMS_FOUND_RESPONSE = "roulette.not_found"
    }

    @Autowired
    lateinit var messages: MessageResolver

    @Test
    fun `should roulette a message`() {
        val validRequest = ROULETTE_REQUEST.format("Test \\n ~~Eliminated~~ \\n Test2")

        val content = doRequestReturningContent(validRequest)

        assertTrue(content.startsWith("Test"))
    }

    @Test
    fun `should not find any message when all lines are striked`() {
        val allStrikedRequest = ROULETTE_REQUEST.format("~~Test~~ \\n ~~Eliminated~~ \\n ~~Test2~~")

        val content = doRequestReturningContent(allStrikedRequest)

        assertEquals(messages.get(NO_ITEMS_FOUND_RESPONSE), content)
    }

    @Test
    fun `should not find any message when there is no message`() {

        val content = doRequestReturningContent(ROULETTE_WITHOUT_MESSAGE_REQUEST)

        assertEquals(messages.get(NO_ITEMS_FOUND_RESPONSE), content)
    }

    @Test
    fun `should not find any message when the message is empty`() {
        val emptyMessageRequest = ROULETTE_REQUEST.format("")

        val content = doRequestReturningContent(emptyMessageRequest)

        assertEquals(messages.get(NO_ITEMS_FOUND_RESPONSE), content)
    }

    private fun doRequestReturningContent(body: String): String {
        val response = RestAssuredMockMvc.given()
            .contentType(ContentType.JSON)
            .body(body)
            .`when`()
            .post(INTERACTIONS_ENDPOINT)
            .then()
            .assertThat(MockMvcResultMatchers.status().isOk)
            .body("type", Matchers.equalTo(4))
            .extract()
            .asString()
        return JsonPath(response).get<String>("data.content").toString()
    }
}
