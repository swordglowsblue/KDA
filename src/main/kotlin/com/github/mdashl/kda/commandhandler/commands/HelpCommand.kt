package com.github.mdashl.kda.commandhandler.commands

import com.github.mdashl.kda.commandhandler.Command
import com.github.mdashl.kda.commandhandler.CommandHandler
import com.github.mdashl.kda.commandhandler.StaffCommand
import com.github.mdashl.kda.commandhandler.annotations.GeneralCommand
import com.github.mdashl.kda.embed
import com.github.mdashl.kda.extensions.containsIgnoreCase

object HelpCommand : Command() {
    override val aliases: List<String> = listOf("help", "commands", "cmds")
    override val description: String = "Shows help"
    override val usage: String = ""

    @GeneralCommand
    fun help() {
        val commands = CommandHandler.commands.filterNot { it is StaffCommand }

        reply(
            embed {
                title("${CommandHandler.jda.selfUser.name} Commands")
                description("**Use `${CommandHandler.options.prefix}help <command>` for additional info.**")
                field {
                    name("Command")
                    value(commands.joinToString("\n") { it.mainAlias })
                }
                field {
                    name("Description")
                    value(commands.joinToString("\n") { it.description })
                }
            }
        )
    }

    @GeneralCommand
    fun help(commandName: String) {
        val command = CommandHandler.commands.find { it.aliases.containsIgnoreCase(commandName) }

        if (command == null) {
            replyError("Command does not exist")
            return
        }

        command.channel = channel
        command.replyHelp()
    }
}
