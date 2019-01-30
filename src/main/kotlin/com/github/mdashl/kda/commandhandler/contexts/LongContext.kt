package com.github.mdashl.kda.commandhandler.contexts

import com.github.mdashl.kda.commandhandler.CommandContext
import com.github.mdashl.kda.extensions.isLong

object LongContext : CommandContext<Long>(Long::class.java) {
    override fun handle(arg: String): Long {
        if (!arg.isLong()) {
            throw IllegalArgumentException("Not a number")
        }

        return arg.toLong()
    }
}
