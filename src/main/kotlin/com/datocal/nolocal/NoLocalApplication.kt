package com.datocal.nolocal

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class NoLocalApplication

fun main(args: Array<String>) {
    runApplication<NoLocalApplication>(*args)
}
