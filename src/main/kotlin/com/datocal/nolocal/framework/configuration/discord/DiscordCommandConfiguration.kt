package com.datocal.nolocal.framework.configuration.discord

import com.datocal.nolocal.framework.controller.discord.DiscordCommandRunner
import com.datocal.nolocal.framework.controller.discord.PingCommandRunner
import org.javacord.api.DiscordApi
import org.springframework.beans.factory.annotation.Qualifier
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
        @Qualifier("registerMap") registerMap: Map<String, DiscordCommandRunner>
    ): DiscordCommandRegister {
        return DiscordCommandRegister(registerMap, discordApi)
    }
}
