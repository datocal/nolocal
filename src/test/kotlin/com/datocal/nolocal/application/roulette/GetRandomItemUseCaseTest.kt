package com.datocal.nolocal.application.roulette

import com.datocal.nolocal.domain.roulette.RandomItemRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

internal class GetRandomItemUseCaseTest {

    private val repository: RandomItemRepository = mock()

    @Test
    fun `should call roulette`() {
        val useCase = GetRandomItemUseCase(repository)
        val request = GetRandomItemUseCaseRequest(items = listOf("a", "a", "a"))

        val response = useCase.execute(request)

        assertEquals("a", response.item)
        verify(repository).save("a")
    }

    @Test
    fun `should return null when there are not valid items in the roulette`() {
        val useCase = GetRandomItemUseCase(repository)
        val request = GetRandomItemUseCaseRequest(items = listOf("~~a~~"))

        val response = useCase.execute(request)

        assertNull(response.item)
        verify(repository, never()).save(anyOrNull())
    }
}
