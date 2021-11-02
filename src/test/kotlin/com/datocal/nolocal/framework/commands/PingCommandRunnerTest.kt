package com.datocal.nolocal.framework.commands

import com.datocal.nolocal.domain.dummy.Ping
import com.datocal.nolocal.framework.discord.model.Data
import com.datocal.nolocal.framework.discord.model.Interaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PingCommandRunnerTest {

    private val commandRunner = PingCommandRunner(Ping())

    @Test
    fun `should say culo message`() {

        val interaction = Interaction(
            type = 2,
            data = Data(
                name = "culo",
                options = emptyList()
            ),
        )
        val response = commandRunner.accept(interaction)

        assertEquals("El culo tuyo", response.data?.content)
    }
}
