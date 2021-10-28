package com.datocal.nolocal.framework

fun getCommandFromRawMessage(message: String): String {
    val splitMessage = message.split(TOKEN_DELIMITER)
    return splitMessage[0].removePrefix(BOT_PREFIX)
}

fun removeCommandFromRawMessage(message: String): String {
    return message
        .removePrefix(BOT_PREFIX)
        .removePrefix(getCommandFromRawMessage(message))
        .removePrefix(TOKEN_DELIMITER)
}

fun rawMessageContainsCommand(message: String): Boolean {
    return message.startsWith(BOT_PREFIX)
}

const val TOKEN_DELIMITER = " "
const val BOT_PREFIX = "+"
const val DEFAULT_COMMAND = "default"
