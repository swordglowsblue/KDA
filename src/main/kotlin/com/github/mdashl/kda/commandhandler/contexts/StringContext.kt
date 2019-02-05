package com.github.mdashl.kda.commandhandler.contexts

import com.github.mdashl.kda.commandhandler.CommandContext
import net.dv8tion.jda.api.entities.Message

object StringContext : CommandContext<String>(String::class.java) {
    override fun handle(message: Message, arg: String): String = arg
}
