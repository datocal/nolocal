package com.datocal.nolocal.framework.configuration.discord

import com.datocal.nolocal.framework.command.DiscordCommandRunner
import org.javacord.api.DiscordApi
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class DiscordCommandRegisterTest {

    private val discordApi = mock(DiscordApi::class.java)

    @Test
    fun `should register listener`() {

        DiscordCommandRegister(discordApi = discordApi, registry = emptyMap())

        verify(discordApi).addMessageCreateListener(any())
    }

    @Test
    fun `should call command for a valid command`() {
        val commandMock = mock(DiscordCommandRunner::class.java)
        val eventMock = mock(MessageCreateEvent::class.java)
        `when`(eventMock.messageContent).thenReturn("+ping")

        DiscordCommandRegister(discordApi = discordApi, registry = mapOf("ping" to commandMock))

        val captor = ArgumentCaptor.forClass(MessageCreateListener::class.java)
        verify(discordApi).addMessageCreateListener(captor.capture())
        captor.value.onMessageCreate(eventMock)
        verify(commandMock).accept(eventMock)
    }

    @Test
    fun `should call command for an invalid command`() {
        val commandMock = mock(DiscordCommandRunner::class.java)
        val eventMock = mock(MessageCreateEvent::class.java)
        `when`(eventMock.messageContent).thenReturn("+pong")

        DiscordCommandRegister(discordApi = discordApi, registry = mapOf("default" to commandMock))

        val captor = ArgumentCaptor.forClass(MessageCreateListener::class.java)
        verify(discordApi).addMessageCreateListener(captor.capture())
        captor.value.onMessageCreate(eventMock)
        verify(commandMock).accept(eventMock)
    }

    @Test
    fun `should not call command for normal message`() {
        val commandMock = mock(DiscordCommandRunner::class.java)
        val eventMock = mock(MessageCreateEvent::class.java)
        `when`(eventMock.messageContent).thenReturn("Hello, friend")

        DiscordCommandRegister(discordApi = discordApi, registry = mapOf("ping" to commandMock))

        val captor = ArgumentCaptor.forClass(MessageCreateListener::class.java)
        verify(discordApi).addMessageCreateListener(captor.capture())
        captor.value.onMessageCreate(eventMock)
        verify(commandMock, never()).accept(eventMock)
    }
}
