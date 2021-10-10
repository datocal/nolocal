package com.davidtca.nolocal.framework.configuration

import com.davidtca.nolocal.domain.Hello
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HelloConfiguration {

    @Bean
    fun hello(): Hello {
        return Hello()
    }
}
