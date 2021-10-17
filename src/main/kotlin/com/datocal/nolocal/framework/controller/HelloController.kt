package com.datocal.nolocal.framework.controller

import com.datocal.nolocal.domain.Hello
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController(private val hello: Hello) {

    @GetMapping("/hello")
    fun hello(): String {
        return hello.hello()
    }
}
