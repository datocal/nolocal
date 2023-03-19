package com.datocal.nolocal.domain.dummy

import com.datocal.nolocal.domain.MessageResolver

class Ping(
    private val messages: MessageResolver,
) {

    fun ping(): String {
        return messages.get("ping.message")
    }
}
