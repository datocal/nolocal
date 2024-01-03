package com.datocal.nolocal.infrastructure.commands

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.reflect.KProperty
import kotlin.reflect.full.companionObject

class CommandsTest {

    private companion object {
        private const val REGEX_TO_MATCH = "^[-_\\p{L}\\p{N}\\p{sc=Deva}\\p{sc=Thai}]{1,32}\$"
    }

    // Commands should match the regular expression in the documentation
    // See: https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-naming
    @Test
    fun `command names should match regular expression`() {
        val commandNames = obtainCommandNamesInTheApplication()

        commandNames.forEach {
            assertTrue(it.matches(Regex(REGEX_TO_MATCH)))
        }
    }

    private fun obtainCommandNamesInTheApplication(): List<String> {
        return Commands::class.companionObject?.members
            ?.filterIsInstance<KProperty<String>>()
            ?.map { it.call(null) } ?: emptyList()
    }
}
