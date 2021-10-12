package com.davidtca.nolocal.framework.controller

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class HelloControllerIntegrationTest : IntegrationTest() {

    @Test
    fun `should say hello`() {
        RestAssuredMockMvc.given()
            .`when`()
            .get("/hello")
            .then()
            .assertThat(MockMvcResultMatchers.status().isOk)
            .body(Matchers.equalTo("hello2"))
    }
}
