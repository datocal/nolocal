package com.datocal.nolocal.application

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class RandomProviderTest {
    // Flaky test that will fail with a very low probability
    @Test
    fun `should return different numbers`() {
        val provider = RandomProvider()

        var sum = 0
        for (i in 0..20) {
            sum += provider.randomOfTen()
        }
        assertTrue(sum > 0)
    }
}
