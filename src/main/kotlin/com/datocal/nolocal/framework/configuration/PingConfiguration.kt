package com.datocal.nolocal.framework.configuration

import com.datocal.nolocal.domain.dummy.Ping
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PingConfiguration {

    @Bean
    fun ping(): Ping {
        return Ping()
    }
}
