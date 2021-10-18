package com.datocal.nolocal.framework

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class TokenUtilityTest {

    @Test
    fun `should return true command for a command`() {
        assertTrue(isCommand("+ping"))
    }

    @Test
    fun `should return false command for a random text`() {
        assertFalse(isCommand("Hello, my friend"))
    }

    @Test
    fun `should return false command for another command`() {
        assertFalse(isCommand("!ping"))
    }

    @Test
    fun `should get empty for empty command`() {
        assertEquals("", command("+"))
    }

    @Test
    fun `should get ping for +ping command`() {
        assertEquals("ping", command("+ping"))
    }

    @Test
    fun `should get ping for +ping command with parameters`() {
        assertEquals("ping", command("+ping hello"))
    }
}
