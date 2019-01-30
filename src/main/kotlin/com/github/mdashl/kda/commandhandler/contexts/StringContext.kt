package com.github.mdashl.kda.commandhandler.contexts

import com.github.mdashl.kda.commandhandler.CommandContext

object StringContext : CommandContext<String>(String::class.java) {
    override fun handle(arg: String): String = arg
}
