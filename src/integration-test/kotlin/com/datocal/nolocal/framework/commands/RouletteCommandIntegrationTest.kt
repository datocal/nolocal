package com.datocal.nolocal.framework.commands

import com.datocal.nolocal.NoLocalApplicationIntegrationTest
import com.datocal.nolocal.framework.controller.IntegrationTest
import io.restassured.http.ContentType
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
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
                                    "content": "Test \n ~~Eliminated~~ \n Test2"
                                }
                            }
                        }
                    },
                    "type" : 2
                }
            """
        private const val ROULETTE_WITHOUT_ITEMS_REQUEST =
            """
                {
                    "data": {
                        "name" : "roulette",
                        "resolved": {
                            "messages": {
                                "867793854505943041": {
                                    "content": "~~Test~~ \n ~~Eliminated~~ \n ~~Test2~~"
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
    }

    @Test
    fun `should roulette a message`() {
        RestAssuredMockMvc.given()
            .contentType(ContentType.JSON)
            .body(ROULETTE_REQUEST)
            .`when`()
            .post(NoLocalApplicationIntegrationTest.INTERACTIONS_ENDPOINT)
            .then()
            .assertThat(MockMvcResultMatchers.status().isOk)
            .body("type", Matchers.equalTo(2))
            .body("data.content", Matchers.startsWith("Test"))
    }

    @Test
    fun `should not find any message when all lines are striked`() {
        RestAssuredMockMvc.given()
            .contentType(ContentType.JSON)
            .body(ROULETTE_WITHOUT_ITEMS_REQUEST)
            .`when`()
            .post(NoLocalApplicationIntegrationTest.INTERACTIONS_ENDPOINT)
            .then()
            .assertThat(MockMvcResultMatchers.status().isOk)
            .body("type", Matchers.equalTo(2))
            .body("data.content", Matchers.equalTo("No items found"))
    }

    @Test
    fun `should not find any message when there is no message`() {
        RestAssuredMockMvc.given()
            .contentType(ContentType.JSON)
            .body(ROULETTE_WITHOUT_MESSAGE_REQUEST)
            .`when`()
            .post(NoLocalApplicationIntegrationTest.INTERACTIONS_ENDPOINT)
            .then()
            .assertThat(MockMvcResultMatchers.status().isOk)
            .body("type", Matchers.equalTo(2))
            .body("data.content", Matchers.equalTo("No items found"))
    }
}
