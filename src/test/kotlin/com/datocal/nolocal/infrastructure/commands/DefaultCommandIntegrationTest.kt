package com.datocal.nolocal.infrastructure.commands

import com.datocal.nolocal.infrastructure.IntegrationTest
import io.restassured.http.ContentType
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class DefaultCommandIntegrationTest : IntegrationTest() {

    private companion object {
        private const val UNKNOWN_COMMAND_REQUEST =
            """
                {
                    "data": {
                        "name" : "aaaaa"
                    }
                }
            """
    }

    @Test
    fun `when the command is unknown should respond with type 1`() {
        RestAssuredMockMvc.given()
            .contentType(ContentType.JSON)
            .body(UNKNOWN_COMMAND_REQUEST)
            .`when`()
            .post(INTERACTIONS_ENDPOINT)
            .then()
            .assertThat(MockMvcResultMatchers.status().isOk)
            .body("type", Matchers.equalTo(1))
    }
}
