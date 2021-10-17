package com.davidtca.nolocal.framework.configuration

import com.davidtca.nolocal.domain.Hello
import com.davidtca.nolocal.framework.configuration.discord.DiscordCommandRegister
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HelloConfiguration {

    @Bean
    fun hello(register: DiscordCommandRegister): Hello {
        return Hello("world")
    }
}
