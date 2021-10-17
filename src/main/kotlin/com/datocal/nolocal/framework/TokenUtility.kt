package com.datocal.nolocal.framework

const val TOKEN_DELIMITER = " "
const val BOT_PREFIX = "+"

fun command(message: String): String {
    val splitMessage = message.split(TOKEN_DELIMITER)
    return splitMessage[0].removePrefix(BOT_PREFIX)
}

fun isCommand(message: String): Boolean {
    return message.startsWith(BOT_PREFIX)
}
