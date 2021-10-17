package com.davidtca.nolocal.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class HelloTest {
    @Test
    fun helloTest() {
        assertEquals("hello, world", Hello("world").hello())
    }
}
