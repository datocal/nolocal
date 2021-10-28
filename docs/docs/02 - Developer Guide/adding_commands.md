---
sidebar_position: 1
---

# Adding commands

Adding commands is pretty straightforward.

You just have to create a Spring bean. The name of the bean must be the command to listen.
This bean has to implement the following interface:

    com.datocal.nolocal.framework.command.DiscordCommandRunner

For example, this would be a ping command:
    
    @Component("ping")
    class PingCommandRunner() : DiscordCommandRunner {

        override fun accept(event: MessageCreateEvent) {
            event.channel.sendMessage("pong")
        }
    }
    
The notation @Component("ping") will create the infrastructure necessary to create the bean and call this Runner when a 
new message appears using the +ping command. You just have to implement the parsing of the message to get the parameters
to know how to run your use case.

The following functions provide an easy way to parse the message from the event.messageContent:

    com.datocal.nolocal.framework.getCommandFromRawMessage(message: String): String
    com.datocal.nolocal.framework.removeCommandFromRawMessage(message: String): String

## Add slash commands
To do.

## How the magic works

The annotation **@Component** is a spring annotation. For those not familiarized with spring, this will initialize this class
calling its constructor and put it in a magic box to take it any time it's needed in a program.

In the shadows, Spring will create a Map<String, DiscordCommandRunner> with all the beans of type DiscordCommandRunner 
in the application, using the bean name (the command string in this case) as the key. We will use this to map any 
command received to this bean.

To use the map, there is a class which initializes the message listener and register 
the way the commands are run:


    @Configuration
    class DiscordCommandRegister(
        private val registry: Map<String, DiscordCommandRunner>,
        private val discordApi: DiscordApi,
    ) {
    
        init {
            registerMessages()
        }
    
        private fun registerMessages() {
            discordApi.addMessageCreateListener { event ->
                if (rawMessageContainsCommand(event.messageContent)) {
                    (registry[getCommandFromRawMessage(event.messageContent)] ?: registry[DEFAULT_COMMAND])!!.accept(event)
                }
            }
        }
    }

Using the discord api, it will create a listener that will extract any command in a message and get the corresponding 
bean using the map.

This class is located at the following package:
    
    com.datocal.nolocal.framework.configuration.discord.DiscordCommandRegister