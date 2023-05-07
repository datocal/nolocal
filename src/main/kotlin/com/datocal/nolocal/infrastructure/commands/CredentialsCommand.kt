package com.datocal.nolocal.infrastructure.commands

import com.datocal.nolocal.application.server.SetUpCredentialsUseCase
import com.datocal.nolocal.application.server.SetUpCredentialsUseCaseRequest
import com.datocal.nolocal.domain.MessageResolver
import com.datocal.nolocal.domain.account.Tenant
import com.datocal.nolocal.domain.server.CloudFlavor
import com.datocal.nolocal.domain.server.NonExistingCloudFlavorException
import com.datocal.nolocal.domain.server.PasswordNotLongEnoughException
import com.datocal.nolocal.infrastructure.discord.model.ApplicationCommandOption
import com.datocal.nolocal.infrastructure.discord.model.ApplicationCommandOptionChoice
import com.datocal.nolocal.infrastructure.discord.model.Command
import com.datocal.nolocal.infrastructure.discord.model.Interaction
import com.datocal.nolocal.infrastructure.discord.model.InteractionResponse
import com.datocal.nolocal.infrastructure.discord.model.OptionProvider
import com.datocal.nolocal.infrastructure.discord.model.RequiredOptionException
import org.springframework.stereotype.Component

@Component("credentials")
@Command(
    command = "credentials",
    description = "Stores a token for a cloud provider using a password",
    type = Command.TYPE_CHAT_INPUT,
    options = CredentialOptionsProvider::class,
)
class CredentialsCommand(
    private val setUpCredentialsUseCase: SetUpCredentialsUseCase,
    private val messageResolver: MessageResolver,
) : DiscordCommand {
    override fun accept(interaction: Interaction): InteractionResponse {
        try{
            setUpCredentialsUseCase.execute(request = SetUpCredentialsUseCaseRequest(
                token = interaction.findOption(name="token")!!,
                userId = interaction.getUserId(),
                tenant = Tenant.DISCORD,
                encryptionKey = interaction.findOption(name="password")!!,
                flavor = CloudFlavor.fromValue(interaction.findOption(name="provider")!!),
            ))
        }catch (e: PasswordNotLongEnoughException){
            return InteractionResponse(messageResolver.get("credentials.errors.password"))
        }catch (e: NonExistingCloudFlavorException){
            return InteractionResponse(messageResolver.get("credentials.errors.flavor"))
        }catch (e: RequiredOptionException){
            return InteractionResponse(messageResolver.get("credentials.errors.required"))
        }catch (e: Exception){
            return InteractionResponse(messageResolver.get("credentials.errors.unspecified"))
        }
        return InteractionResponse(messageResolver.get("credentials.success"))
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
