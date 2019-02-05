package com.github.mdashl.kda.commandhandler.contexts

import com.github.mdashl.kda.commandhandler.CommandContext
import com.github.mdashl.kda.extensions.isInt
import net.dv8tion.jda.api.entities.Message

object IntContext : CommandContext<Int>(Int::class.java) {
    override fun handle(message: Message, arg: String): Int {
        if (!arg.isInt()) {
            throw IllegalArgumentException("Not a number")
        }

        return arg.toInt()
    }
}
