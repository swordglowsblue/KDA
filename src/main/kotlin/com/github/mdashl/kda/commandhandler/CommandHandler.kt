package com.github.mdashl.kda.commandhandler

import com.github.mdashl.kda.commandhandler.annotations.SubCommand
import com.github.mdashl.kda.commandhandler.commands.HelpCommand
import com.github.mdashl.kda.commandhandler.contexts.BooleanContext
import com.github.mdashl.kda.commandhandler.contexts.IntContext
import com.github.mdashl.kda.commandhandler.contexts.LongContext
import com.github.mdashl.kda.commandhandler.contexts.StringContext
import com.github.mdashl.kda.extensions.containsIgnoreCase
import com.github.mdashl.kda.extensions.handlerOf
import com.github.mdashl.kda.extensions.removeDoubleSpaces
import com.github.mdashl.kda.extensions.toInt
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.net.URISyntaxException
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors

object CommandHandler {
    lateinit var jda: JDA
    lateinit var options: Options

    private val POOL = Executors.newFixedThreadPool(4).asCoroutineDispatcher()

    val commands: ArrayList<Command> = ArrayList()
    val contexts: ArrayList<CommandContext<*>> = ArrayList()

    private fun getCommand(message: String): Command? {
        val prefix = options.prefix
        val s = message.split(" ")[0]

        return commands.find { command -> command.aliases.any { s.equals(prefix + it, true) } }
    }

    private fun getCommandContext(type: Class<*>): CommandContext<*> =
        contexts.find { it.type == type }
            ?: throw IllegalArgumentException("No command context for ${type.simpleName}")

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
                command.replyError("You are not allowed to use this command")
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
                method.invoke(command, *getCommandParameters(method, args.drop(offset.toInt())))
            } catch (exception: Throwable) {
                handleCommandException(command, exception)
            }
        }
    }

    @Throws(IllegalArgumentException::class)
    private fun getCommandParameters(method: Method, args: List<String>): Array<*> {
        return method.parameters.mapIndexed { index, parameter ->
            val type = parameter.type
            val arg = args[index]

            val context = getCommandContext(type)

            context.handle(arg)
        }.toTypedArray()
    }

    private fun handleCommandException(command: Command, exception: Throwable) {
        when (exception) {
            is IllegalArgumentException -> {
                exception.cause?.let { handleCommandException(command, it) }
                    ?: command.replyError(exception.message.toString())
            }
            is InsufficientPermissionException -> command.reply("I need **${exception.permission.getName()}** permission to work")
            is InvocationTargetException -> handleCommandException(command, exception.cause!!)
            is ExecutionException -> handleCommandException(command, exception.cause!!)
            is URISyntaxException -> command.replyError("Illegal character") // Don't send exception's message, because it contains URL that may contain some credentials
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
        StringContext.register()
        IntContext.register()
        LongContext.register()
        BooleanContext.register()
    }

    private fun registerDefaultCommands() {
        HelpCommand.register()
    }

    fun setup(jda: JDA, options: Options) {
        this.jda = jda
        this.options = options

        registerListener()
        registerDefaults()
    }

    data class Options(val prefix: String, val ownerId: String, val staff: List<String> = emptyList()) {
        val owner: User
            get() = jda.getUserById(ownerId)
    }
}
