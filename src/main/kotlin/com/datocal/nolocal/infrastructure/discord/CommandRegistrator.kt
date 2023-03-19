package com.datocal.nolocal.infrastructure.discord

import com.datocal.nolocal.infrastructure.commands.DiscordCommand
import com.datocal.nolocal.infrastructure.discord.client.DiscordApiClient
import com.datocal.nolocal.infrastructure.discord.model.ApplicationCommand
import com.datocal.nolocal.infrastructure.discord.model.Command
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile(value = ["production"])
class CommandRegistrator(
    private val commandExecutors: List<DiscordCommand>,
    private val client: DiscordApiClient,
) : ApplicationRunner {

    private fun toAnnotatedCommand(command: DiscordCommand): Command? =
        command.javaClass.getDeclaredAnnotation(Command::class.java)

    private fun toApplicationCommand(command: Command) = ApplicationCommand(
        name = command.command,
        description = command.description,
        type = command.type,
    )

    private fun notOnCurrentCommands(currentCommands: List<ApplicationCommand>, it: ApplicationCommand) =
        !currentCommands.contains(it)

    override fun run(args: ApplicationArguments?) {
        val currentCommands = client.getCommands()
        commandExecutors
            .mapNotNull(this::toAnnotatedCommand)
            .map(this::toApplicationCommand)
            .filter { notOnCurrentCommands(currentCommands, it) }
            .forEach(this.client::register)
    }
}
