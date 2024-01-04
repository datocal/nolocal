package com.datocal.nolocal.infrastructure.discord.client

import com.datocal.nolocal.infrastructure.discord.model.ApplicationCommand
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.client.WebClient

class DiscordApplicationsClient(
    private val client: WebClient,
) {

    private val logger: Logger = LoggerFactory.getLogger(DiscordApplicationsClient::class.java)

    fun getCommands(): List<ApplicationCommand> {
        return client
            .get()
            .uri("/commands")
            .retrieve()
            .toEntityList(ApplicationCommand::class.java)
            .block()!!
            .body
            ?.toList() ?: emptyList()
    }

    fun register(applicationCommand: ApplicationCommand) {
        val registeredCommand = client
            .post()
            .uri("/commands")
            .bodyValue(applicationCommand)
            .retrieve()
            .toEntity(ApplicationCommand::class.java)
            .block()
        logger.info("Command registered:  $registeredCommand")
    }
}
