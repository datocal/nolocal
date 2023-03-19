package com.datocal.nolocal.domain.roulette

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class RouletteTest {

    @Test
    fun `should filter items and return null`() {
        val response = Roulette(listOf(RouletteItem("~~aa~~"), RouletteItem("~~bb~~"))).random()
        assertNull(response)
    }

    @Test
    fun `should filter items and return a item`() {
        val response = Roulette(
            listOf(
                RouletteItem("~~aa~~"),
                RouletteItem("bb"),
                RouletteItem("~~cc~~"),
                RouletteItem("~~dd~~"),
            ),
        ).random()

        assertNotNull(response)
        assertEquals("bb", response!!.value)
    }
}
