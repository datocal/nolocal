package com.datocal.nolocal.framework.configuration

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NewRelicConfiguration {
    @Bean
    fun commonTags(): MeterRegistryCustomizer<MeterRegistry> {
        return MeterRegistryCustomizer { r: MeterRegistry -> r.config().commonTags("AppName", "NoLocal") }
    }
}
