package com.datocal.nolocal.framework

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class TokenUtilityTest {

    @Test
    fun `should return true command for a command`() {
        assertTrue(rawMessageContainsCommand("+ping"))
    }

    @Test
    fun `should return false command for a random text`() {
        assertFalse(rawMessageContainsCommand("Hello, my friend"))
    }

    @Test
    fun `should return false command for another command`() {
        assertFalse(rawMessageContainsCommand("!ping"))
    }

    @Test
    fun `should get empty for empty command`() {
        assertEquals("", getCommandFromRawMessage("+"))
    }

    @Test
    fun `should get ping for +ping command`() {
        assertEquals("ping", getCommandFromRawMessage("+ping"))
    }

    @Test
    fun `should get ping for +ping command with parameters`() {
        assertEquals("ping", getCommandFromRawMessage("+ping hello"))
    }
}
