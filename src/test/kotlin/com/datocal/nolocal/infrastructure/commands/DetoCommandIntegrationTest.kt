package com.datocal.nolocal.infrastructure.commands

import com.datocal.nolocal.infrastructure.IntegrationTest
import io.restassured.http.ContentType
import io.restassured.module.mockmvc.RestAssuredMockMvc
import io.restassured.path.json.JsonPath
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class DetoCommandIntegrationTest : IntegrationTest() {
    private companion object {

        private const val DETO_REQUEST =
            """
                {
                    "data": {
                        "name" : "deto",
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
    }

    @Test
    fun `should deto a message`() {
        val validRequest = DETO_REQUEST.format("Abada")

        val content = doRequestReturningContent(validRequest)

        Assertions.assertTrue(listOf("Abada", "Adada").contains(content))
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
