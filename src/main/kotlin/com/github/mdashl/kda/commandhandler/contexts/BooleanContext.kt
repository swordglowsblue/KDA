package com.github.mdashl.kda.commandhandler.contexts

import com.github.mdashl.kda.commandhandler.CommandContext
import com.github.mdashl.kda.extensions.i18n
import com.github.mdashl.kda.extensions.isBoolean
import net.dv8tion.jda.api.entities.Message

object BooleanContext : CommandContext<Boolean>(Boolean::class.java) {

    override fun handle(message: Message, arg: String): Boolean =
        arg.takeIf { it.isBoolean() }?.toBoolean()
            ?: throw IllegalArgumentException("commandhandler.contexts.boolean.error".i18n())

}
