package com.datocal.nolocal.framework.configuration

import com.newrelic.telemetry.Attributes
import com.newrelic.telemetry.micrometer.NewRelicRegistry
import com.newrelic.telemetry.micrometer.NewRelicRegistryConfig
import io.micrometer.core.instrument.config.MeterFilter
import io.micrometer.core.instrument.util.NamedThreadFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration
import org.springframework.boot.actuate.autoconfigure.metrics.export.simple.SimpleMetricsExportAutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.InetAddress
import java.time.Duration

@Configuration
@AutoConfigureBefore(CompositeMeterRegistryAutoConfiguration::class, SimpleMetricsExportAutoConfiguration::class)
@AutoConfigureAfter(
    MetricsAutoConfiguration::class
)
@ConditionalOnClass(NewRelicRegistry::class)
class NewRelicConfiguration {

    @Value("\${secrets.newrelic.apikey}")
    lateinit var apikey: String

    @Bean
    fun newRelicConfig(): NewRelicRegistryConfig {
        return object : NewRelicRegistryConfig {
            override fun get(key: String): String? {
                return null
            }

            override fun apiKey(): String {
                return apikey
            }

            override fun step(): Duration {
                return Duration.ofSeconds(5)
            }

            override fun serviceName(): String {
                return "NoLocalBoot"
            }

            override fun uri(): String {
                return "https://metric-api.eu.newrelic.com/metric/v1"
            }
        }
    }

    @Bean
    fun newRelicMeterRegistry(config: NewRelicRegistryConfig?): NewRelicRegistry {
        val newRelicRegistry = NewRelicRegistry.builder(config)
            .commonAttributes(
                Attributes()
                    .put("host", InetAddress.getLocalHost().hostName)
            )
            .build()
        newRelicRegistry.config().meterFilter(MeterFilter.ignoreTags("plz_ignore_me"))
        newRelicRegistry.config().meterFilter(MeterFilter.denyNameStartsWith("jvm.threads"))
        newRelicRegistry.start(NamedThreadFactory("newrelic.micrometer.registry"))
        return newRelicRegistry
    }
}
