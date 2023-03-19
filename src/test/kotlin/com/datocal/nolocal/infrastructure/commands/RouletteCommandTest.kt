package com.datocal.nolocal.infrastructure.commands

import com.datocal.nolocal.application.roulette.GetRandomItemUseCase
import com.datocal.nolocal.application.roulette.GetRandomItemUseCaseRequest
import com.datocal.nolocal.application.roulette.GetRandomItemUseCaseResponse
import com.datocal.nolocal.domain.MessageResolver
import com.datocal.nolocal.infrastructure.discord.model.Data
import com.datocal.nolocal.infrastructure.discord.model.Interaction
import com.datocal.nolocal.infrastructure.discord.model.PartialMessage
import com.datocal.nolocal.infrastructure.discord.model.Resolved
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import java.util.stream.Stream

internal class RouletteCommandTest {

    private val useCase: GetRandomItemUseCase = mock(GetRandomItemUseCase::class.java)

    private val messageResolver: MessageResolver = mock(MessageResolver::class.java)

    private val runner = RouletteCommand(useCase, messageResolver)

    @ParameterizedTest
    @MethodSource("interactionShouldProvideRequest")
    fun `should build request`(interaction: Interaction, request: GetRandomItemUseCaseRequest) {
        `when`(useCase.execute(request)).thenReturn(GetRandomItemUseCaseResponse(null))
        `when`(messageResolver.get("roulette.not_found")).thenReturn("Not found")

        runner.accept(interaction = interaction)

        verify(useCase).execute(request = request)
    }

    companion object {
        @JvmStatic
        fun interactionShouldProvideRequest(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(nullDataInteraction(), emptyUseCaseRequest()),
                Arguments.of(nullResolvesInteraction(), emptyUseCaseRequest()),
                Arguments.of(emptyMessagesInteraction(), emptyUseCaseRequest()),
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

        private fun emptyMessagesInteraction(): Interaction {
            return Interaction(
                data = Data(
                    name = "roulette",
                    resolved = Resolved(),
                ),
                type = 1,
            )
        }

        private fun nullEntriesInteraction(): Interaction {
            return Interaction(
                data = Data(
                    name = "roulette",
                    resolved = Resolved(
                        messages = mapOf(
                            "" to null,
                            "a" to PartialMessage(),
                        ),
                    ),
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
                                content = "abc",
                            ),
                            "456" to PartialMessage(
                                content = "def",
                            ),
                        ),
                    ),
                ),
                type = 1,
            )
        }

        private fun emptyUseCaseRequest(): Any {
            return GetRandomItemUseCaseRequest(
                emptyList(),
            )
        }

        private fun actualUseCaseRequest(): Any {
            return GetRandomItemUseCaseRequest(
                listOf("abc", "def"),
            )
        }
    }
}
