package com.github.mdashl.kda.commandhandler

import net.dv8tion.jda.api.entities.Message

abstract class CommandContext<T>(val type: Class<T>) {

    abstract fun handle(message: Message, text: String, arg: String): T

    fun register() {
        CommandHandler.CONTEXTS += this
    }

}
