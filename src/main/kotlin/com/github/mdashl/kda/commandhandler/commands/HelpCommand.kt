package com.github.mdashl.kda.commandhandler.commands

import com.github.mdashl.kda.KDA.client
import com.github.mdashl.kda.commandhandler.Command
import com.github.mdashl.kda.commandhandler.CommandHandler
import com.github.mdashl.kda.commandhandler.annotations.GeneralCommand
import com.github.mdashl.kda.extensions.containsIgnoreCase
import com.github.mdashl.kda.extensions.i18n

object HelpCommand : Command() {

    override val aliases: List<String> = listOf("help", "commands", "cmds")
    override val description: String = "commandhandler.commands.help.description".i18n()
    override val usage: String = "commandhandler.commands.help.usage".i18n()

    @GeneralCommand
    fun help() {
        val commands = CommandHandler.commands.filter(Command::displayInHelp)

        reply {
            title = "commandhandler.commands.help.reply.title".i18n(client.selfUser.name)
            description += "commandhandler.commands.help.reply.description".i18n(CommandHandler.prefix)
            field {
                name = "commandhandler.commands.help.reply.fields.command.name".i18n()
                value =
                    "commandhandler.commands.help.reply.fields.command.value".i18n(commands.joinToString("\n") { it.name })
            }
            field {
                name = "commandhandler.commands.help.reply.fields.description.name".i18n()
                value =
                    "commandhandler.commands.help.reply.fields.description.value".i18n(commands.joinToString("\n") { it.description })
            }
        }
    }

    @GeneralCommand
    fun help(name: String) {
        val command = CommandHandler.commands.find { it.aliases.containsIgnoreCase(name) }
            ?: throw IllegalArgumentException("commandhandler.commands.help.reply.error".i18n())

        command.channel = channel
        command.replyHelp()
    }

}
