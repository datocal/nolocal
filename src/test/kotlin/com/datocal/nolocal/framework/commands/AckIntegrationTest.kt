package com.datocal.nolocal.framework.commands

import com.datocal.nolocal.framework.IntegrationTest
import io.restassured.http.ContentType
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class AckIntegrationTest : IntegrationTest() {

    @Test
    fun `should ack a discord call`() {
        RestAssuredMockMvc.given()
            .contentType(ContentType.JSON)
            .body(
                mapOf(
                    "type" to 1
                )
            )
            .`when`()
            .post("/discord/interactions")
            .then()
            .assertThat(MockMvcResultMatchers.status().isOk)
            .body("type", Matchers.equalTo(1))
    }
}
