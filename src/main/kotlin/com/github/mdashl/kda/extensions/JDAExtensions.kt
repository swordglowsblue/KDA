package com.github.mdashl.kda.extensions

import com.github.mdashl.kda.commandhandler.Command
import com.github.mdashl.kda.commandhandler.CommandContext
import com.github.mdashl.kda.commandhandler.CommandHandler
import net.dv8tion.jda.api.JDA

fun JDA.setupCommandHandler(options: CommandHandler.Options) {
    CommandHandler.setup(this, options)
}

fun JDA.registerCommand(command: Command) {
    CommandHandler.commands += command
}

fun JDA.registerCommandContext(context: CommandContext<*>) {
    CommandHandler.contexts += context
}
