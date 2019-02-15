package com.github.mdashl.kda.commandhandler.contexts

import com.github.mdashl.kda.commandhandler.CommandContext
import com.github.mdashl.kda.extensions.i18n
import com.github.mdashl.kda.extensions.isLong
import net.dv8tion.jda.api.entities.Message

object LongContext : CommandContext<Long>(Long::class.java) {

    override fun handle(message: Message, text: String, arg: String): Long =
        arg.takeIf(String::isLong)?.toLong()
            ?: throw IllegalArgumentException("commandhandler.contexts.long.error".i18n())

}
