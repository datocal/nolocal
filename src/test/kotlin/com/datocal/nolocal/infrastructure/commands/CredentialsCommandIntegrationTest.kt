package com.datocal.nolocal.infrastructure.commands

import com.datocal.nolocal.infrastructure.IntegrationTest
import io.restassured.http.ContentType
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class CredentialsCommandIntegrationTest : IntegrationTest() {
    private companion object {
        private const val CREDENTIALS_REQUEST =
            """
                {
                    "data": {
                        "name" : "credentials"
                    }
                }
            """
    }

    @Test
    fun `should respond test`() {
        RestAssuredMockMvc.given()
            .contentType(ContentType.JSON)
            .body(CREDENTIALS_REQUEST)
            .`when`()
            .post(INTERACTIONS_ENDPOINT)
            .then()
            .assertThat(MockMvcResultMatchers.status().isOk)
            .body("type", Matchers.equalTo(4))
            .body("data.content", Matchers.equalTo("test"))
    }
}
