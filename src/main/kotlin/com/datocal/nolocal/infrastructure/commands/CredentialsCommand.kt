package com.datocal.nolocal.infrastructure.commands

import com.datocal.nolocal.infrastructure.discord.model.ApplicationCommandOption
import com.datocal.nolocal.infrastructure.discord.model.ApplicationCommandOptionChoice
import com.datocal.nolocal.infrastructure.discord.model.Command
import com.datocal.nolocal.infrastructure.discord.model.Interaction
import com.datocal.nolocal.infrastructure.discord.model.InteractionResponse
import com.datocal.nolocal.infrastructure.discord.model.OptionProvider
import org.springframework.stereotype.Component

@Component(Commands.CREDENTIALS)
@Command(
    command = Commands.CREDENTIALS,
    description = "Stores a token for a cloud provider using a password",
    type = Command.TYPE_CHAT_INPUT,
    options = CredentialOptionsProvider::class,
)
class CredentialsCommand : DiscordCommand {
    override fun accept(interaction: Interaction): InteractionResponse {
        return InteractionResponse("test")
    }
}

class CredentialOptionsProvider : OptionProvider() {
    override fun options(): List<ApplicationCommandOption> {
        return listOf(
            ApplicationCommandOption(
                name = "provider",
                description = "The cloud provider to store the token",
                type = 3,
                required = true,
                choices = listOf(
                    ApplicationCommandOptionChoice(
                        name = "Digital Ocean",
                        value = "digital_ocean",
                    ),
                ),
            ),
            ApplicationCommandOption(
                name = "token",
                description = "The token to store",
                type = 3,
                required = true,
            ),
            ApplicationCommandOption(
                name = "password",
                description = "password used to access this resource",
                type = 3,
                required = true,
            ),
        )
    }
}
