package com.datocal.nolocal

import org.junit.jupiter.api.Test

internal class NoLocalApplicationIntegrationTest {

    companion object {
        const val INTERACTIONS_ENDPOINT = "/discord/interactions"
    }

    @Test
    // Dummy test to get Coverage
    fun mainTest() {
        main(arrayOf("--server.port=0"))
    }
}
