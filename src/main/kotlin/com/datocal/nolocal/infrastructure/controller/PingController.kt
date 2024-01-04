package com.datocal.nolocal.infrastructure.controller

import com.datocal.nolocal.domain.dummy.Ping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PingController(private val ping: Ping) {
    @GetMapping("/ping")
    fun ping(): String {
        return ping.ping()
    }
}
