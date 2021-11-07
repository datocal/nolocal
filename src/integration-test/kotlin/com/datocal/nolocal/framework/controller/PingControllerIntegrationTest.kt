package com.datocal.nolocal.framework.controller

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class PingControllerIntegrationTest : IntegrationTest() {

    @Test
    fun `should say hello`() {
        RestAssuredMockMvc.given()
            .`when`()
            .get("/ping")
            .then()
            .assertThat(MockMvcResultMatchers.status().isOk)
            .body(Matchers.equalTo("El culo tuyo"))
    }
}
