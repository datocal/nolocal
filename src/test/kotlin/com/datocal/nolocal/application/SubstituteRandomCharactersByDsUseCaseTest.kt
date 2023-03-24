package com.datocal.nolocal.application

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class SubstituteRandomCharactersByDsUseCaseTest {
    private val randomProvider: RandomProvider = mock()

    private val useCase = SubstituteRandomCharactersByDsUseCase(randomProvider)

    @Test
    fun `should not replace unchangeable characters`() {
        val givenAStringWithOnlyUnchangeableChars = "aeiuo "

        val result = useCase.execute(givenAStringWithOnlyUnchangeableChars)

        assertEquals(givenAStringWithOnlyUnchangeableChars, result)
    }

    @Test
    fun `should replace characters when happens`() {
        val givenAStringToChange = "abada quedabra"
        whenever(randomProvider.randomOfTen()).thenReturn(1)

        val result = useCase.execute(givenAStringToChange)

        assertEquals("adada duedadda", result)
    }


    @Test
    fun `should replace characters when happens with uppercase`() {
        val givenAStringToChange = "aBada quedabra"
        whenever(randomProvider.randomOfTen()).thenReturn(1)

        val result = useCase.execute(givenAStringToChange)

        assertEquals("aDada duedadda", result)
    }


    @Test
    fun `should not replace characters when not happens`() {
        val givenAStringToChange = "abada quedabra"
        whenever(randomProvider.randomOfTen()).thenReturn(0)

        val result = useCase.execute(givenAStringToChange)

        assertEquals("abada quedabra", result)
    }
}
