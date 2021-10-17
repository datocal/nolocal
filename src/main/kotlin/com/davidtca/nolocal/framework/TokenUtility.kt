package com.davidtca.nolocal.framework

class TokenUtility {
    companion object {
        const val TOKEN_DELIMITER = " "
        const val BOT_PREFIX = "+"
    }
}

fun command(message: String): String {
    val splitMessage = message.split(TokenUtility.TOKEN_DELIMITER)
    return splitMessage[0].removePrefix(TokenUtility.BOT_PREFIX)
}

fun isCommand(message: String): Boolean {
    return message.startsWith(TokenUtility.BOT_PREFIX)
}
