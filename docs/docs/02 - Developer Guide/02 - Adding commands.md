---
sidebar_position: 2
id: adding
---

# Adding commands

## Registering commands
To make a command available in the server it's needed to register it in the discord API.
It can be registered as a global or a specific server command.

This process is not automated, as the time of writing this, the process is as simple as to make a POST request with the 
command specification. You can check the specific documentation [in the following link](https://discord.com/developers/docs/interactions/application-commands#slash-commands)

## Programming slash commands

Adding commands is pretty straightforward.

You just have to create a Spring bean. The name of the bean must be the command to listen.
This bean has to implement the following interface:

    com.datocal.nolocal.framework.commands.DiscordCommandRunner

For example, this would be a ping command:

    @Component("culo")
    class PingCommandRunner(private val ping: Ping) : DiscordCommandRunner {

        override fun accept(interaction: Interaction) : InteractionResponse {
            return buildResponse(ping.ping())
        }
    
        private fun buildResponse(message: String) = InteractionResponse(
            type = 4,
            data = InteractionResponseData(
                tts = false,
                content = message,
                embeds = emptyList(),
                allowed_mentions = AllowedMentions(
                    parse = emptyList()
                )
            )
        )
    }

    
The notation @Component("ping") will create the infrastructure necessary to create the bean and call this Runner when the 
ping command is invoked. You just have to implement the method returning an InteractionResponse.

### How the magic works

The annotation **@Component** is a spring annotation. For those not familiarized with spring, this will initialize this class
calling its constructor and put it in a magic box to take it any time it's needed in a program.

In the shadows, Spring will create a Map<String, DiscordCommandRunner> with all the beans of type DiscordCommandRunner 
in the application, using the bean name (the command string in this case) as the key. We will use this to map any 
command received to this bean.

To use the map, there is a controller who listens for upcoming commands, **the interactions endpoint**


    @RestController
    class InteractionsController(
        private val commands: Map<String, DiscordCommandRunner>
    ) {
    
        @PostMapping("/discord/interactions")
        fun execute(@RequestBody interaction: Interaction): InteractionResponse {
            return commands[interaction.data?.name]?.accept(interaction) ?: defaultResponse()
        }
    
        //ACK Discord PING checks
        private fun defaultResponse() = InteractionResponse(type = 1, data = null)
    }

Discord requires this endpoint to receive upcoming commands. In production, this endpoint is protected by a reverse proxy
to only allow requests coming from discord. The proxy will require a header with the request encrypted with a private 
key owned by discord.

This class is located at the following package:
    
    com.datocal.nolocal.framework.discord.controller.InteractionsController