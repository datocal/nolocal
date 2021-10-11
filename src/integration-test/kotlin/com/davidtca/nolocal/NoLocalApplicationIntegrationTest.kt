package com.davidtca.nolocal

import org.junit.jupiter.api.Test

internal class NoLocalApplicationIntegrationTest {

    @Test
    //Dummy test to get Coverage
    fun mainTest() {
        main(arrayOf("-server.port=0"))
    }
}
