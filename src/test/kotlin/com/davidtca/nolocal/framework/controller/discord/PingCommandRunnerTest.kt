package com.davidtca.nolocal.framework.controller.discord

import org.javacord.api.entity.channel.TextChannel
import org.javacord.api.event.message.MessageCreateEvent
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

internal class PingCommandRunnerTest {

    private val commandRunner = PingCommandRunner()

    private val eventMock: MessageCreateEvent = mock(MessageCreateEvent::class.java)
    private var channelMock: TextChannel = mock(TextChannel::class.java)

    @BeforeEach
    fun setUp() {
        Mockito.`when`(eventMock.channel).thenReturn(channelMock)
    }

    @Test
    fun `should say ping message`() {
        Mockito.`when`(eventMock.messageContent).thenReturn("+ping")

        commandRunner.accept(eventMock)

        verify(channelMock).sendMessage("El ping tuyo")
    }
}
