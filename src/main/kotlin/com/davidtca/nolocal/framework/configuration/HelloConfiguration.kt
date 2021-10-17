package com.davidtca.nolocal.framework.configuration

import com.davidtca.nolocal.domain.Hello
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HelloConfiguration {

    @Value("\${secrets.discord.token}")
    private lateinit var token: String

    @Bean
    fun hello(): Hello {
        return Hello(token)
    }
}
