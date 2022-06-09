package com.datocal.nolocal.infrastructure.discord.client

import com.datocal.nolocal.infrastructure.discord.model.ApplicationCommand
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

class DiscordApiClient(
    private val client: WebClient
) {

    private val logger: Logger = LoggerFactory.getLogger(DiscordApiClient::class.java)

    fun getCommands(): List<ApplicationCommand> {
        return client
            .get()
            .uri("/commands")
            .retrieve()
            .bodyToFlux(ApplicationCommand::class.java)
            .collectList()
            .block() ?: emptyList()
    }

    fun register(applicationCommand: ApplicationCommand) {
        client
            .post()
            .uri("/commands")
            .body(Mono.just(applicationCommand), ApplicationCommand::class.java)
            .retrieve()
            .bodyToMono(ApplicationCommand::class.java)
            .subscribe { logger.info("Command registered:  $it") }
    }
}
