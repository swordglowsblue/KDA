package com.github.mdashl.kda.commandhandler

import com.github.mdashl.kda.builders.EmbedBuilder
import com.github.mdashl.kda.commandhandler.CommandHandler.jda
import com.github.mdashl.kda.commandhandler.annotations.GeneralCommand
import com.github.mdashl.kda.commandhandler.annotations.SubCommand
import com.github.mdashl.kda.embed
import net.dv8tion.jda.api.entities.*
import java.lang.reflect.Method

abstract class Command {
    abstract val aliases: List<String>
    abstract val description: String
    abstract val usage: String

    open val sendTyping: Boolean = false

    val mainAlias: String by lazy { aliases[0] }

    lateinit var guild: Guild
    lateinit var member: Member
    lateinit var channel: TextChannel
    lateinit var message: Message

    internal val generalCommands: List<Method> =
        this::class.java.methods.filter { it.isAnnotationPresent(GeneralCommand::class.java) }
    internal val subCommands: List<Method> =
        this::class.java.methods.filter { it.isAnnotationPresent(SubCommand::class.java) }

    fun reply(message: String) {
        channel.sendMessage(message).queue()
    }

    fun reply(embed: MessageEmbed) {
        channel.sendMessage(embed).queue()
    }

    inline fun reply(init: EmbedBuilder.() -> Unit) {
        reply(embed(init))
    }

    fun replyHelp() {
        reply(
            embed {
                title("Command $mainAlias")
                thumbnail(jda.selfUser.effectiveAvatarUrl)
                field {
                    name("Description")
                    value(description)
                    inline(false)
                }
                field {
                    name("Aliases")
                    value(aliases.joinToString())
                    inline(false)
                }
                field {
                    name("Usage")
                    value("`${CommandHandler.options.prefix}$mainAlias $usage`")
                    inline(false)
                }
            }
        )
    }

    fun replyError(error: String) {
        reply(
            embed {
                title("Error")
                description(error)
                color(204, 0, 0)
            }
        )
    }

    fun replyUncaughtException(exception: Throwable) {
        reply(
            embed {
                title("Uncaught Exception")
                description(exception.toString())
                footer {
                    text("Report this to ${CommandHandler.options.owner.asTag}")
                }
                color(204, 0, 0)
            }
        )
    }
}
