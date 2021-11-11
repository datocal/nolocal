package com.datocal.nolocal.framework.commands

import com.datocal.nolocal.framework.discord.model.Data
import com.datocal.nolocal.framework.discord.model.Interaction
import com.datocal.nolocal.framework.discord.model.PartialMessage
import com.datocal.nolocal.framework.discord.model.Resolved
import com.datocal.nolocal.usecases.roulette.GetRandomItemUseCase
import com.datocal.nolocal.usecases.roulette.GetRandomItemUseCaseRequest
import com.datocal.nolocal.usecases.roulette.GetRandomItemUseCaseResponse
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import java.util.stream.Stream

internal class RouletteCommandRunnerTest {

    private val useCase: GetRandomItemUseCase = mock(GetRandomItemUseCase::class.java)

    private val runner = RouletteCommandRunner(useCase)

    @ParameterizedTest
    @MethodSource("interactionShouldProvideRequest")
    fun shouldBuildRequest(interaction: Interaction, request: GetRandomItemUseCaseRequest) {
        `when`(useCase.execute(request)).thenReturn(GetRandomItemUseCaseResponse(null))
        runner.accept(interaction = interaction)
        verify(useCase).execute(request = request)
    }

    companion object {
        @JvmStatic
        fun interactionShouldProvideRequest(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(nullDataInteraction(), emptyUseCaseRequest()),
                Arguments.of(nullResolvesInteraction(), emptyUseCaseRequest()),
                Arguments.of(nullMessagesInteraction(), emptyUseCaseRequest()),
                Arguments.of(nullEntriesInteraction(), emptyUseCaseRequest()),
                Arguments.of(goodInteraction(), actualUseCaseRequest()),
            )
        }

        private fun nullDataInteraction(): Interaction {
            return Interaction(
                data = null,
                type = 1,
            )
        }

        private fun nullResolvesInteraction(): Interaction {
            return Interaction(
                data = Data(
                    name = "roulette",
                ),
                type = 1,
            )
        }

        private fun nullMessagesInteraction(): Interaction {
            return Interaction(
                data = Data(
                    name = "roulette",
                    resolved = Resolved()
                ),
                type = 1,
            )
        }

        private fun nullEntriesInteraction(): Interaction {
            return Interaction(
                data = Data(
                    name = "roulette",
                    resolved = Resolved(
                        messages = mapOf("" to null)
                    )
                ),
                type = 1,
            )
        }

        private fun goodInteraction(): Interaction {
            return Interaction(
                data = Data(
                    name = "roulette",
                    resolved = Resolved(
                        messages = mapOf(
                            "123" to PartialMessage(
                                content = "abc"
                            ),
                            "456" to PartialMessage(
                                content = "def"
                            )
                        )
                    )
                ),
                type = 1,
            )
        }

        private fun emptyUseCaseRequest(): Any {
            return GetRandomItemUseCaseRequest(
                emptyList()
            )
        }

        private fun actualUseCaseRequest(): Any {
            return GetRandomItemUseCaseRequest(
                listOf("abc", "def")
            )
        }
    }
}
