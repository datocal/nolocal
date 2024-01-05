package com.datocal.nolocal.infrastructure.discord.client

import com.datocal.nolocal.infrastructure.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class DiscordWebhooksClientTest : IntegrationTest() {

    @Autowired
    private lateinit var client: DiscordWebhooksClient

    @Autowired
    private lateinit var stubs: DiscordWebhooksClientStubs

    @Test
    fun `should request discord a followup for a message`()  {
        val expectations = stubs.`a followup returns 200`(INTERACTION_TOKEN, INTERACTION_FOLLOWUP_MESSAGE)

        client.followup(
            INTERACTION_TOKEN,
            FollowUpRequest(
                content = INTERACTION_FOLLOWUP_MESSAGE,
            ),
        )

        stubs.`a follow up message have been send`(expectations)
    }

    private companion object {
        private const val INTERACTION_TOKEN = "123"
        private const val INTERACTION_FOLLOWUP_MESSAGE = "Hello!"
    }
}
