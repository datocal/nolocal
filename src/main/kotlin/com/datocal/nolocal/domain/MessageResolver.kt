package com.datocal.nolocal.domain

interface MessageResolver {
    fun get(key: String): String
}
