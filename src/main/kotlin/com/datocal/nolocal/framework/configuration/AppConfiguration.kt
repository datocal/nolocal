package com.datocal.nolocal.framework.configuration

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
}