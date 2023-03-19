package com.datocal.nolocal.infrastructure.configuration

import com.datocal.nolocal.domain.MessageResolver
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.stereotype.Component
import java.util.Locale

@Component
class MessageResolverImpl(
    private val messageSource: ResourceBundleMessageSource,
) : MessageResolver {
    override fun get(key: String): String {
        return messageSource.getMessage(key, emptyArray(), Locale("es"))
    }
}
