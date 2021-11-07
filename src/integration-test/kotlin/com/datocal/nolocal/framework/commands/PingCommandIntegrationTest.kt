package com.datocal.nolocal.framework.commands

import com.datocal.nolocal.NoLocalApplicationIntegrationTest.Companion.INTERACTIONS_ENDPOINT
import com.datocal.nolocal.framework.controller.IntegrationTest
import io.restassured.http.ContentType
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class PingCommandIntegrationTest : IntegrationTest() {

    private companion object {
        private const val PING_REQUEST =
            """
                {
                    "data": {
                        "name" : "culo"
                    }
                }
            """
    }

    @Test
    fun `should respond el culo tuyo`() {
        RestAssuredMockMvc.given()
            .contentType(ContentType.JSON)
            .body(PING_REQUEST)
            .`when`()
            .post(INTERACTIONS_ENDPOINT)
            .then()
            .assertThat(MockMvcResultMatchers.status().isOk)
            .body("type", Matchers.equalTo(4))
            .body("data.content", Matchers.equalTo("El culo tuyo"))
    }
}
