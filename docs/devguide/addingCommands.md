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

    com.datocal.nolocal.infrastructure.commands.DiscordCommand

For example, this would be a ping command:

    @Component("ping")
    class PingCommand(private val ping: Ping) : DiscordCommand {
    
        override fun accept(interaction: Interaction): InteractionResponse {
            return InteractionResponse(ping.ping())
        }
    }

And, for the discord registration to work, it must be documented with the @Command annotation. Description cannot be 
informed for message commands.

    @Command(
        command = "ping",
        description = "Just a ping command",
        type = Command.TYPE_CHAT_INPUT,
    )

    
The notation @Component("ping") will create the infrastructure necessary to create the bean and call this Runner when the 
ping command is invoked. You just have to implement the method returning an InteractionResponse.

The notation @Command will add the command to discord at startup if it's not already in the discord configuration.

### How the magic works

The annotation **@Component** is a spring annotation. For those not familiarized with spring, this will initialize this class
calling its constructor and put it in a magic box to take it any time it's needed in a program.

In the shadows, Spring will create a Map<String, DiscordCommand> with all the beans of type DiscordCommand 
in the application, using the bean name (the command string in this case) as the key. We will use this to map any 
command received to this bean.

To use the map, there is a controller who listens for upcoming commands, **the interactions endpoint**


    @RestController
    class InteractionsController(
        private val commands: Map<String, DiscordCommand>
    ) {
    
        @PostMapping("/discord/interactions")
        fun execute(@RequestBody interaction: Interaction): InteractionResponse {
            return commands[interaction.command]?.accept(interaction) ?: defaultResponse()
        }
    
        //ACK Discord PING checks
        private fun defaultResponse() = InteractionResponse()
    }

Discord requires this endpoint to receive upcoming commands. In production, this endpoint is protected by a reverse proxy
to only allow requests coming from discord. The proxy will require a header with the request encrypted with a private 
key owned by discord.

This class is located at the following package:
    
    com.datocal.nolocal.infrastructure.discord.controller.InteractionsController

The registration on commands via discord works by a bean with a @PostConstruct annotation that register the command via
api, using the class in:

    com.datocal.nolocal.infrastructure.discord.CommandRegistrator