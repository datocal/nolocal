package com.datocal.nolocal.framework.commands

import com.datocal.nolocal.framework.discord.model.Data
import com.datocal.nolocal.framework.discord.model.Interaction
import com.datocal.nolocal.framework.discord.model.InteractionResponse
import com.datocal.nolocal.framework.discord.model.InteractionResponseData
import com.datocal.nolocal.framework.discord.model.Option
import com.datocal.nolocal.usecases.roulette.GetRandomItemUseCase
import com.datocal.nolocal.usecases.roulette.GetRandomItemUseCaseRequest
import com.datocal.nolocal.usecases.roulette.GetRandomItemUseCaseResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

internal class RouletteCommandRunnerTest {

    private val useCase = mock(GetRandomItemUseCase::class.java)

    private val commandRunner = RouletteCommandRunner(useCase)

    @Test
    fun `should request random item with a 3-element list`() {
        val interaction = Interaction(
            type = 2L,
            data = Data(
                name = "roulette",
                options = listOf(TEST, TEST_2_DISCARD, TEST_3).map { Option(name = "item", value = it) }
            )
        )
        Mockito.`when`(
            useCase.execute(
                GetRandomItemUseCaseRequest(listOf(TEST, TEST_2_DISCARD, TEST_3))
            )
        ).thenReturn(GetRandomItemUseCaseResponse(TEST))

        val response = commandRunner.accept(interaction)

        assertEquals(
            InteractionResponse(
                type = 2,
                data = InteractionResponseData(
                    content = TEST,
                )
            ),
            response
        )
        verify(useCase).execute(request = GetRandomItemUseCaseRequest(listOf(TEST, TEST_2_DISCARD, TEST_3)))
    }

    @Test
    fun `should return custom message when all elements are filtered`() {
        val interaction = Interaction(
            type = 2L,
            data = Data(
                name = "roulette",
                options = listOf(TEST_DISCARD, TEST_2_DISCARD).map { Option(name = "item", value = it) }
            )
        )
        Mockito.`when`(
            useCase.execute(
                GetRandomItemUseCaseRequest(listOf(TEST_DISCARD, TEST_2_DISCARD))
            )
        ).thenReturn(GetRandomItemUseCaseResponse(null))

        val response = commandRunner.accept(interaction)

        assertEquals(
            InteractionResponse(
                type = 2,
                data = InteractionResponseData(
                    content = "No items found",
                )
            ),
            response
        )
        verify(useCase).execute(request = GetRandomItemUseCaseRequest(listOf(TEST_DISCARD, TEST_2_DISCARD)))
    }

    companion object {
        private const val TEST_2_DISCARD = "~~Test2~~"
        private const val TEST_DISCARD = "~~Test~~"
        private const val TEST = "Test"
        private const val TEST_3 = "Test3"
    }
}
