package com.datocal.nolocal.domain

class Hello(private val token: String) {

    fun hello(): String {
        return "hello, $token"
    }
}
