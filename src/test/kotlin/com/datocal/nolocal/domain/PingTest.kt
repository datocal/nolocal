package com.datocal.nolocal.domain

import com.datocal.nolocal.domain.dummy.Ping
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PingTest {
    @Test
    fun pingTest() {
        assertEquals("El culo tuyo", Ping().ping())
    }
}
