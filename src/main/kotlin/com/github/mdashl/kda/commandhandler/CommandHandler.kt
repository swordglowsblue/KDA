package com.github.mdashl.kda.commandhandler

import com.github.mdashl.kda.KDA.jda
import com.github.mdashl.kda.Text
import com.github.mdashl.kda.commandhandler.annotations.SubCommand
import com.github.mdashl.kda.commandhandler.commands.HelpCommand
import com.github.mdashl.kda.commandhandler.contexts.*
import com.github.mdashl.kda.extensions.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException
import java.awt.Color
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.net.URISyntaxException
import java.util.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors

object CommandHandler {

    private val POOL = Executors.newFixedThreadPool(4).asCoroutineDispatcher()

    lateinit var prefix: String
    var defaultColor: Color? = null
    lateinit var errorColor: Color
    var displayStaffCommandsInHelp: Boolean = false

    val COMMANDS: ArrayList<Command> = ArrayList()
    val CONTEXTS: ArrayList<CommandContext<*>> = ArrayList()

    private fun getCommand(message: String): Command? {
        val s = message.split(" ")[0]

        return COMMANDS.find { command -> command.aliases.any { s.equals(prefix + it, true) } }
    }

    private fun getCommandContext(type: Class<*>): CommandContext<*> =
        CONTEXTS.find { it.type == type }
            ?: throw IllegalArgumentException(
                "commandhandler.no_command_context".i18n()
                    .placeholder("type", type.simpleName)
            )

    private fun registerListener() {
        jda.handlerOf<GuildMessageReceivedEvent> { event ->
            val message = event.message
            val content = message.contentRaw.removeDoubleSpaces()
            val command = getCommand(content) ?: return@handlerOf
            val guild = event.guild
            val member = event.member
            val user = event.author
            val channel = event.channel
            val args = content.split(" ").drop(1)

            command.guild = guild
            command.member = member
            command.channel = channel
            command.message = message

            if (user.isBot) {
                return@handlerOf
            }

            if (!command.checkPermission()) {
                command.replyError("commandhandler.user_no_permission".i18n())
                return@handlerOf
            }

            if (args.isEmpty()) {
                invokeCommand(command, command.generalCommands.find { it.parameterCount == 0 }, channel)
                return@handlerOf
            }

            val method =
                command.subCommands.find { it.getAnnotation(SubCommand::class.java).value.containsIgnoreCase(args[0]) && it.parameterCount + 1 == args.size }

            method?.let {
                invokeCommand(command, it, channel, args, true)
                return@handlerOf
            }

            invokeCommand(command, command.generalCommands.find { it.parameterCount == args.size }, channel, args)
        }
    }

    private fun invokeCommand(
        command: Command,
        method: Method?,
        channel: TextChannel,
        args: List<String> = emptyList(),
        offset: Boolean = false
    ) {
        if (method == null) {
            command.replyHelp()
            return
        }

        if (command.sendTyping) {
            channel.sendTyping().queue()
        }

        GlobalScope.launch(POOL) {
            try {
                method.invoke(command, *getCommandParameters(command, method, args.drop(offset.toInt())))
            } catch (exception: Throwable) {
                handleCommandException(command, exception)
            }
        }
    }

    @Throws(IllegalArgumentException::class)
    private fun getCommandParameters(command: Command, method: Method, args: List<String>): Array<*> {
        return method.parameters.mapIndexed { index, parameter ->
            val type = parameter.type
            val arg = args[index]

            when (type) {
                String::class.java -> arg
                Text::class.java -> args.drop(index).joinToString(" ")
                else -> getCommandContext(type).handle(command.message, arg)
            }
        }.toTypedArray()
    }

    private fun handleCommandException(command: Command, exception: Throwable) {
        when (exception) {
            is IllegalArgumentException -> {
                exception.cause
                    ?.let { handleCommandException(command, it) }
                    ?: command.replyError(exception.message.toString())
            }
            is InsufficientPermissionException -> command.reply(
                "commandhandler.bot_no_permission".i18n()
                    .placeholder("permission", exception.permission.getName())
            )
            is InvocationTargetException -> handleCommandException(command, exception.cause!!)
            is ExecutionException -> handleCommandException(command, exception.cause!!)
            is URISyntaxException -> command.replyError("commandhandler.illegal_character".i18n())
            else -> {
                command.replyUncaughtException(exception)
                exception.printStackTrace()
            }
        }
    }

    private fun registerDefaults() {
        registerDefaultContexts()
        registerDefaultCommands()
    }

    private fun registerDefaultContexts() {
        IntContext.register()
        LongContext.register()
        BooleanContext.register()
        MemberContext.register()
        TextChannelContext.register()
    }

    private fun registerDefaultCommands() {
        HelpCommand.register()
    }

    fun setup(options: Options) {
        this.prefix = options.prefix
        this.defaultColor = options.defaultColor
        this.errorColor = options.errorColor
        this.displayStaffCommandsInHelp = options.displayStaffCommandsInHelp

        registerListener()
        registerDefaults()
    }

    data class Options(
        val prefix: String,
        val defaultColor: Color? = null,
        val errorColor: Color = Color(204, 0, 0),
        val displayStaffCommandsInHelp: Boolean = false
    )

}
