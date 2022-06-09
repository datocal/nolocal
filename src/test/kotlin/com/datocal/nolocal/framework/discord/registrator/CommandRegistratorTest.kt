package com.datocal.nolocal.framework.discord.registrator

import com.datocal.nolocal.framework.commands.DiscordCommand
import com.datocal.nolocal.framework.discord.CommandRegistrator
import com.datocal.nolocal.framework.discord.client.DiscordApiClient
import com.datocal.nolocal.framework.discord.model.ApplicationCommand
import com.datocal.nolocal.framework.discord.model.Command
import com.datocal.nolocal.framework.discord.model.Interaction
import com.datocal.nolocal.framework.discord.model.InteractionResponse
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

internal class CommandRegistratorTest {

    private val apiClient: DiscordApiClient = mock()

    @Test
    fun `should not register a command when it is not annotated`() {
        `when the discord api doesnt provide any command`()
        val commandExecutors = `given a discord command without annotation`()

        CommandRegistrator(commandExecutors, apiClient).registerCommands()

        verify(apiClient, never()).register(`application command`(1))
    }

    @Test
    fun `should not register a command if it is already registered on discord`() {
        `when the discord api provides a command `()
        val commandExecutors = `given an annotated discord command`()

        CommandRegistrator(commandExecutors, apiClient).registerCommands()

        verify(apiClient, never()).register(`application command`())
    }

    @Test
    fun `should register a command when there are no commands on discord`() {
        `when the discord api doesnt provide any command`()
        val commandExecutors = `given an annotated discord command`()

        CommandRegistrator(commandExecutors, apiClient).registerCommands()

        verify(apiClient).register(`application command`())
    }

    @Test
    fun `should register new commands when some of them are already registered`() {
        `when the discord api provides a command `()
        val commandExecutors = `given some annotated discord commands`()

        CommandRegistrator(commandExecutors, apiClient).registerCommands()

        verify(apiClient, times(2)).register(any())
        verify(apiClient, never()).register(`application command`(1))
        verify(apiClient).register(`application command`(2))
        verify(apiClient).register(`application command`(3))
    }

    private fun `when the discord api provides a command `() {
        `when`(apiClient.getCommands()).thenReturn(listOf(`application command`()))
    }

    private fun `when the discord api doesnt provide any command`() {
        `when`(apiClient.getCommands()).thenReturn(emptyList())
    }

    private fun `given a discord command without annotation`(): List<DiscordCommand> {
        return listOf(mock())
    }

    private fun `given an annotated discord command`(): List<DiscordCommand> {
        return listOf(DiscordCommandMock())
    }

    private fun `given some annotated discord commands`(): List<DiscordCommand> =
        listOf(DiscordCommandMock2(), DiscordCommandMock3())

    private fun `application command`(n: Int = 1) = ApplicationCommand(
        name = "command-$n",
        description = "description",
        type = 3,
    )
}

@Command(
    command = "command-1",
    description = "description",
    type = Command.TYPE_MESSAGE,
)
internal class DiscordCommandMock : DiscordCommand {
    override fun accept(interaction: Interaction) = InteractionResponse()
}

@Command(
    command = "command-2",
    description = "description",
    type = Command.TYPE_MESSAGE,
)
internal class DiscordCommandMock2 : DiscordCommand {
    override fun accept(interaction: Interaction) = InteractionResponse()
}

@Command(
    command = "command-3",
    description = "description",
    type = Command.TYPE_MESSAGE,
)
internal class DiscordCommandMock3 : DiscordCommand {
    override fun accept(interaction: Interaction) = InteractionResponse()
}
