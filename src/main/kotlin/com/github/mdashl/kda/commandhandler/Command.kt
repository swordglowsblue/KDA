package com.github.mdashl.kda.commandhandler

import com.github.mdashl.kda.KDA
import com.github.mdashl.kda.KDA.jda
import com.github.mdashl.kda.builders.EmbedBuilder
import com.github.mdashl.kda.commandhandler.annotations.GeneralCommand
import com.github.mdashl.kda.commandhandler.annotations.SubCommand
import com.github.mdashl.kda.extensions.i18n
import com.github.mdashl.kda.extensions.placeholder
import net.dv8tion.jda.api.entities.*
import java.lang.reflect.Method

abstract class Command {
    abstract val aliases: List<String>
    abstract val description: String
    abstract val usage: String

    val name by lazy { aliases[0] }

    open val sendTyping: Boolean = false

    lateinit var guild: Guild
    lateinit var member: Member
    lateinit var channel: TextChannel
    lateinit var message: Message

    internal val generalCommands: List<Method> =
        this::class.java.methods.filter { it.isAnnotationPresent(GeneralCommand::class.java) }
    internal val subCommands: List<Method> =
        this::class.java.methods.filter { it.isAnnotationPresent(SubCommand::class.java) }

    fun register() {
        CommandHandler.commands += this
    }

    fun reply(message: String) {
        channel.sendMessage(message).queue()
    }

    fun reply(embed: MessageEmbed) {
        channel.sendMessage(embed).queue()
    }

    inline fun reply(init: EmbedBuilder.() -> Unit) {
        reply(EmbedBuilder().apply(init).apply { CommandHandler.defaultColor?.let { color(it) } }.build())
    }

    fun replyHelp() {
        reply {
            title(
                "commandhandler.reply.help.title".i18n()
                    .placeholder("name", name)
            )
            thumbnail(jda.selfUser.effectiveAvatarUrl)
            field {
                name("commandhandler.reply.help.fields.description.name".i18n())
                value(
                    "commandhandler.reply.help.fields.description.value".i18n()
                        .placeholder("description", description)
                )
                inline(false)
            }
            field {
                name("commandhandler.reply.help.fields.aliases.name".i18n())
                value(
                    "commandhandler.reply.help.fields.aliases.value".i18n()
                        .placeholder("aliases", aliases.joinToString())
                )
                inline(false)
            }
            field {
                name("commandhandler.reply.help.fields.usage.name".i18n())
                value(
                    "commandhandler.reply.help.fields.usage.value".i18n()
                        .placeholder("prefix", CommandHandler.prefix)
                        .placeholder("name", name)
                        .placeholder("usage", usage)
                )
                inline(false)
            }
        }
    }

    fun replyError(error: String) {
        reply {
            title("commandhandler.reply.error.title".i18n())
            description(
                "commandhandler.reply.error.description".i18n()
                    .placeholder("error", error)
            )
            color(204, 0, 0)
        }
    }

    fun replyUncaughtException(exception: Throwable) {
        reply {
            title("commandhandler.reply.uncaughtexception.title".i18n())
            description(
                "commandhandler.reply.uncaughtexception.description".i18n()
                    .placeholder("exception", exception.toString())
            )
            footer {
                text(
                    "commandhandler.reply.uncaughtexception.footer".i18n()
                        .placeholder("owner", KDA.owner.asTag)
                )
            }
            color(204, 0, 0)
        }
    }

    open fun checkPermission(): Boolean = true
}
