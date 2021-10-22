package com.datocal.nolocal.usecases.roulette

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class GetRandomItemUseCaseTest {

    @Test
    fun `should call roulette`() {
        val useCase = GetRandomItemUseCase()
        val request = GetRandomItemUseCaseRequest(items = listOf("a", "a", "a"))

        val response = useCase.execute(request)

        assertEquals("a", response.item)
    }

    @Test
    fun `should return null when there are not valid items in the roulette`() {
        val useCase = GetRandomItemUseCase()
        val request = GetRandomItemUseCaseRequest(items = listOf("~~a~~"))

        val response = useCase.execute(request)

        assertNull(response.item)
    }
}
