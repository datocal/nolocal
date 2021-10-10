package com.davidtca.nolocal

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NoLocalApplication

fun main(args: Array<String>) {
    runApplication<NoLocalApplication>(*args)
}
