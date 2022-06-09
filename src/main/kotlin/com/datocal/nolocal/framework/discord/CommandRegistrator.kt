package com.datocal.nolocal.framework.discord

import com.datocal.nolocal.framework.commands.DiscordCommand
import com.datocal.nolocal.framework.discord.client.DiscordApiClient
import com.datocal.nolocal.framework.discord.model.ApplicationCommand
import com.datocal.nolocal.framework.discord.model.Command
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
@Profile("production")
class CommandRegistrator(
    private val commandExecutors: List<DiscordCommand>,
    private val client: DiscordApiClient,
) {

    @PostConstruct
    fun registerCommands() {
        val currentCommands = client.getCommands()
        commandExecutors
            .mapNotNull(this::toAnnotatedCommand)
            .map(this::toApplicationCommand)
            .filter { notOnCurrentCommands(currentCommands, it) }
            .forEach(this.client::register)
    }

    private fun toAnnotatedCommand(command: DiscordCommand): Command? =
        command.javaClass.getDeclaredAnnotation(Command::class.java)

    private fun toApplicationCommand(command: Command) = ApplicationCommand(
        name = command.command,
        description = command.description,
        type = command.type,
    )

    private fun notOnCurrentCommands(currentCommands: List<ApplicationCommand>, it: ApplicationCommand) =
        !currentCommands.contains(it)
}
