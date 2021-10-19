package com.datocal.nolocal.framework.command

import com.datocal.nolocal.usecases.roulette.GetRandomItemUseCase
import com.datocal.nolocal.usecases.roulette.GetRandomItemUseCaseRequest
import com.datocal.nolocal.usecases.roulette.GetRandomItemUseCaseResponse
import org.javacord.api.entity.channel.TextChannel
import org.javacord.api.event.message.MessageCreateEvent
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

internal class RouletteCommandRunnerTest{

    private val useCase = mock(GetRandomItemUseCase::class.java)
    private val eventMock: MessageCreateEvent = mock(MessageCreateEvent::class.java)
    private var channelMock: TextChannel = mock(TextChannel::class.java)

    private val commandRunner = RouletteCommandRunner(useCase)

    @BeforeEach
    fun setUp() {
        Mockito.`when`(eventMock.channel).thenReturn(channelMock)
    }

    @Test
    fun `should request random item with a 3-element list`(){
        Mockito.`when`(eventMock.messageContent).thenReturn("+roulette Test \n ~~Test2~~ \n Test3")
        Mockito.`when`(
            useCase.execute(
                GetRandomItemUseCaseRequest(listOf("Test", "~~Test2~~", "Test3"))
            )
        ).thenReturn(GetRandomItemUseCaseResponse("Test"))

        commandRunner.accept(eventMock)

        verify(useCase).execute(request = GetRandomItemUseCaseRequest(listOf("Test", "~~Test2~~", "Test3")))
    }

    @Test
    fun `should return custom message when all elements are filtered`(){
        Mockito.`when`(eventMock.messageContent).thenReturn("+roulette ~~Test~~ \n ~~Test2~~")
        Mockito.`when`(
            useCase.execute(
                GetRandomItemUseCaseRequest(listOf("~~Test~~", "~~Test2~~"))
            )
        ).thenReturn(GetRandomItemUseCaseResponse(null))
        commandRunner.accept(eventMock)

        verify(channelMock).sendMessage("No items found")
    }

}