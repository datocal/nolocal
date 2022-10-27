package com.datocal.nolocal.infrastructure

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.jupiter.api.BeforeEach
import org.mockserver.client.MockServerClient
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.MapPropertySource
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.containers.MockServerContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.utility.DockerImageName

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(
    initializers = [
        MockServerInitializer::class
    ]
)
class IntegrationTest {

    companion object {
        const val INTERACTIONS_ENDPOINT = "/discord/interactions"
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var mockServerClient: MockServerClient

    @BeforeEach
    fun setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc)
    }
}

class MockServerInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    private val container: MockServerContainer =
        MockServerContainer(DockerImageName.parse("mockserver/mockserver:5.14.0"))

    private val logger = LoggerFactory.getLogger(MockServerInitializer::class.java)
    private var logConsumer = Slf4jLogConsumer(logger)

    override fun initialize(context: ConfigurableApplicationContext) {
        container.start()
        container.followOutput(logConsumer)

        val client = MockServerClient(container.host, container.serverPort)
        context.beanFactory.registerSingleton("mockServerClient", client)
        context.environment.propertySources.addFirst(
            MapPropertySource(
                "testcontainers",
                mapOf(
                    "discord.api.host" to "http://" + container.host,
                    "discord.api.port" to container.serverPort,
                )
            )
        )
    }
}
