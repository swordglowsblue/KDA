package com.github.mdashl.kda.commandhandler

abstract class CommandContext<T>(val type: Class<T>) {
    abstract fun handle(arg: String): T
}
