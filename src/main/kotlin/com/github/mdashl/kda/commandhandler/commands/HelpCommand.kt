package com.github.mdashl.kda.commandhandler.commands

import com.github.mdashl.kda.KDA.jda
import com.github.mdashl.kda.commandhandler.Command
import com.github.mdashl.kda.commandhandler.CommandHandler
import com.github.mdashl.kda.commandhandler.annotations.GeneralCommand
import com.github.mdashl.kda.extensions.containsIgnoreCase
import com.github.mdashl.kda.extensions.i18n
import com.github.mdashl.kda.extensions.placeholder

object HelpCommand : Command() {

    override val aliases: List<String> = listOf("help", "commands", "cmds")
    override val description: String = "commandhandler.commands.help.description".i18n()
    override val usage: String = ""

    @GeneralCommand
    fun help() {
        val commands = CommandHandler.COMMANDS.filter(Command::displayInHelp)

        reply {
            title(
                "commandhandler.commands.help.reply.title".i18n()
                    .placeholder("bot", jda.selfUser.name)
            )
            description(
                "commandhandler.commands.help.reply.description".i18n()
                    .placeholder("prefix", CommandHandler.prefix)
            )
            field {
                name("commandhandler.commands.help.reply.fields.command.name".i18n())
                value(
                    "commandhandler.commands.help.reply.fields.command.value".i18n()
                        .placeholder("commands", commands.joinToString("\n") { it.name })
                )
            }
            field {
                name("commandhandler.commands.help.reply.fields.description.name".i18n())
                value(
                    "commandhandler.commands.help.reply.fields.description.value".i18n()
                        .placeholder("commands", commands.joinToString("\n") { it.description })
                )
            }
        }
    }

    @GeneralCommand
    fun help(commandName: String) {
        val command = CommandHandler.COMMANDS.find { it.aliases.containsIgnoreCase(commandName) }

        if (command == null) {
            replyError(
                "commandhandler.commands.help.reply.error".i18n()
                    .placeholder("command", commandName)
            )
            return
        }

        command.channel = channel
        command.replyHelp()
    }

}
