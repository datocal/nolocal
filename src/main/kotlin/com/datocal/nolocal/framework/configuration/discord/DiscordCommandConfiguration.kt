package com.datocal.nolocal.framework.configuration.discord

import com.datocal.nolocal.domain.dummy.Ping
import com.datocal.nolocal.framework.command.DiscordCommandRunner
import com.datocal.nolocal.framework.command.PingCommandRunner
import com.datocal.nolocal.framework.command.RouletteCommandRunner
import org.javacord.api.DiscordApi
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DiscordCommandConfiguration {

    @Bean
    fun pingCommandRunner(ping: Ping): PingCommandRunner {
        return PingCommandRunner(ping)
    }

    @Bean
    fun rouletteCommandRunner(): RouletteCommandRunner {
        return RouletteCommandRunner()
    }

    @Bean("registerMap")
    fun registerMap(
        pingCommandRunner: PingCommandRunner,
        roulette: RouletteCommandRunner,
    ): Map<String, DiscordCommandRunner> {
        return mapOf(
            "default" to pingCommandRunner,
            "roulette" to roulette,
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
