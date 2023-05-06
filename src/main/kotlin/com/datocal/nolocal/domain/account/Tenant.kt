package com.datocal.nolocal.domain.account

enum class Tenant(private val code: String) {
    DISCORD("discord"),
    ;

    override fun toString(): String {
        return code
    }
}
