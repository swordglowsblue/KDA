package com.github.mdashl.kda.commandhandler.contexts

import com.github.mdashl.kda.commandhandler.CommandContext
import com.github.mdashl.kda.extensions.isBoolean

object BooleanContext : CommandContext<Boolean>(Boolean::class.java) {
    override fun handle(arg: String): Boolean {
        if (!arg.isBoolean()) {
            throw IllegalArgumentException("Not a boolean")
        }

        return arg.toBoolean()
    }
}
