package com.datocal.nolocal.infrastructure

import io.restassured.module.mockmvc.RestAssuredMockMvc
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.MockServerContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.utility.DockerImageName

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(
    initializers = [
        ContainerInitializer::class,
    ],
)
class IntegrationTest {

    companion object {
        const val INTERACTIONS_ENDPOINT = "/discord/interactions"
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc)
    }
}

class ContainerInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    private val logger = LoggerFactory.getLogger(ContainerInitializer::class.java)
    private var logConsumer = Slf4jLogConsumer(logger)
    private val mockserverContainer: MockServerContainer by lazy {
        MockServerContainer(
            DockerImageName.parse("mockserver/mockserver")
                .withTag("mockserver-" + MockServerClient::class.java.getPackage().implementationVersion),
        )
    }
    private val redisContainer: GenericContainer<Nothing> by lazy {
        GenericContainer<Nothing> (
            DockerImageName
                .parse("redis")
                .withTag("latest"),
        ).apply { withExposedPorts(REDIS_PORT) }
    }

    private fun mockserver(context: ConfigurableApplicationContext) {
        mockserverContainer.start()
        mockserverContainer.followOutput(logConsumer)

        val client = MockServerClient(mockserverContainer.host, mockserverContainer.serverPort)
        context.beanFactory.registerSingleton("mockServerClient", client)
        context.environment.propertySources.addFirst(
            MapPropertySource(
                "mockServerProperties",
                mapOf(
                    "discord.api.host" to "http://" + mockserverContainer.host,
                    "discord.api.port" to mockserverContainer.serverPort,
                ),
            ),
        )
    }

    private fun redis(context: ConfigurableApplicationContext) {
        redisContainer.start()
        redisContainer.followOutput(logConsumer)

        context.environment.propertySources.addFirst(
            MapPropertySource(
                "redisProperties",
                mapOf(
                    "redis.host" to redisContainer.host,
                    "redis.port" to redisContainer.getMappedPort(REDIS_PORT),
                ),
            ),
        )
    }

    override fun initialize(context: ConfigurableApplicationContext) {
        runBlocking {
            launch {
                redis(context)
            }
            launch {
                mockserver(context)
            }
        }
    }

    private companion object {
        private const val REDIS_PORT = 6379
    }
}
