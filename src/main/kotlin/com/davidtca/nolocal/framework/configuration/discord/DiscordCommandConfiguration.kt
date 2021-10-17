package com.davidtca.nolocal.framework.configuration.discord

import com.davidtca.nolocal.framework.controller.discord.DiscordCommandRunner
import com.davidtca.nolocal.framework.controller.discord.PingCommandRunner
import org.javacord.api.DiscordApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DiscordCommandConfiguration {

    @Bean
    fun pingCommandRunner(): PingCommandRunner {
        return PingCommandRunner()
    }

    @Bean("registerMap")
    fun registerMap(
        pingCommandRunner: PingCommandRunner,
    ): Map<String, DiscordCommandRunner> {
        return mapOf(
            "ping" to pingCommandRunner,
            "culo" to pingCommandRunner,
        )
    }

    @Bean
    fun discordCommandRegister(
        discordApi: DiscordApi,
        registerMap: Map<String, DiscordCommandRunner>
    ): DiscordCommandRegister {
        return DiscordCommandRegister(registerMap, discordApi)
    }
}
