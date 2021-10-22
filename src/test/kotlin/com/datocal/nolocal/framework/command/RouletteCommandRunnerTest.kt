package com.datocal.nolocal.framework.command

import com.datocal.nolocal.usecases.roulette.GetRandomItemUseCase
import com.datocal.nolocal.usecases.roulette.GetRandomItemUseCaseRequest
import com.datocal.nolocal.usecases.roulette.GetRandomItemUseCaseResponse
import org.javacord.api.entity.channel.TextChannel
import org.javacord.api.event.message.MessageCreateEvent
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

internal class RouletteCommandRunnerTest {

    private val useCase = mock(GetRandomItemUseCase::class.java)
    private val eventMock: MessageCreateEvent = mock(MessageCreateEvent::class.java)
    private var channelMock: TextChannel = mock(TextChannel::class.java)

    private val commandRunner = RouletteCommandRunner(useCase)

    @BeforeEach
    fun setUp() {
        Mockito.`when`(eventMock.channel).thenReturn(channelMock)
    }

    @Test
    fun `should request random item with a 3-element list`() {
        Mockito.`when`(eventMock.messageContent).thenReturn("+roulette $TEST \n $TEST_2_DISCARD \n $TEST_3")
        Mockito.`when`(
            useCase.execute(
                GetRandomItemUseCaseRequest(listOf(TEST, TEST_2_DISCARD, TEST_3))
            )
        ).thenReturn(GetRandomItemUseCaseResponse(TEST))

        commandRunner.accept(eventMock)

        verify(useCase).execute(request = GetRandomItemUseCaseRequest(listOf(TEST, TEST_2_DISCARD, TEST_3)))
    }

    @Test
    fun `should return custom message when all elements are filtered`() {
        Mockito.`when`(eventMock.messageContent).thenReturn("+roulette $TEST_DISCARD \n $TEST_2_DISCARD")
        Mockito.`when`(
            useCase.execute(
                GetRandomItemUseCaseRequest(listOf(TEST_DISCARD, TEST_2_DISCARD))
            )
        ).thenReturn(GetRandomItemUseCaseResponse(null))
        commandRunner.accept(eventMock)

        verify(channelMock).sendMessage("No items found")
    }

    companion object {
        private const val TEST_2_DISCARD = "~~Test2~~"
        private const val TEST_DISCARD = "~~Test~~"
        private const val TEST = "Test"
        private const val TEST_3 = "Test3"
    }
}
