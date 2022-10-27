package com.datocal.nolocal.infrastructure.configuration

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.Locale

@Configuration
class AppConfiguration {

    @Bean
    fun localeResolver(): LocaleResolver {
        val sessionLocaleResolver = SessionLocaleResolver()
        sessionLocaleResolver.setDefaultLocale(Locale("es"))
        return sessionLocaleResolver
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        val objectMapper = jacksonObjectMapper()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return objectMapper
    }
}
