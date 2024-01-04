package com.datocal.nolocal.infrastructure.discord.model

import kotlin.reflect.KClass

/**
 * Annotation to register discord commands. A description can only be provided to the TYPE_CHAT_INPUT commands,
 * otherwise the application will fail to load the commands.
 *
 * input:
 *    command:     Name of the command to be created and the one the users will interact with. it could be a slash
 *                 command like /culo or a message/user command that will be shown as a dropdown in the discord
 *                 interface.
 *    description: Description that will be shown on the slash commands to help a user to know what the command does.
 *    type:        Type of the command, more information about types can be found at the discord official documentation:
 *                 https://discord.com/developers/docs/tutorials/upgrading-to-application-commands#types-of-commands
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Command(
    val command: String,
    val description: String = "",
    val type: Int = TYPE_CHAT_INPUT,
    val options: KClass<out OptionProvider> = OptionProvider::class,
) {
    companion object {
        const val TYPE_CHAT_INPUT: Int = 1
        const val TYPE_USER: Int = 2
        const val TYPE_MESSAGE: Int = 3
    }
}
