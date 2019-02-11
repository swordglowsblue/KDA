package com.github.mdashl.kda.commandhandler

import com.github.mdashl.kda.Text
import net.dv8tion.jda.api.entities.Message

abstract class CommandContext<T>(val type: Class<T>) {

    abstract fun handle(message: Message, text: Text, arg: String): T

    fun register() {
        CommandHandler.CONTEXTS += this
    }

}
