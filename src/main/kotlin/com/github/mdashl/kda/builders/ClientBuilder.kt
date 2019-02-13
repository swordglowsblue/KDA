package com.github.mdashl.kda.builders

import com.github.mdashl.kda.KDA
import com.github.mdashl.kda.commandhandler.CommandHandler
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import java.awt.Color
import java.util.*

class ClientBuilder {

    lateinit var token: String
    lateinit var owner: String

    var staff: List<String> = ArrayList()
    var locale: Locale = Locale.US
    var commandhandler: CommandHandlerOptions? = null

    var activity: Activity? = null
    var status: OnlineStatus? = null

    inline fun commandhandler(init: CommandHandlerOptions.() -> Unit) {
        commandhandler = CommandHandlerOptions().apply(init)
    }

    fun build(): JDA =
        JDABuilder(token)
            .apply {
                setActivity(activity)
                setStatus(status)
            }
            .build()
            .awaitReady()
            .also {
                KDA.setup(
                    it,
                    owner,
                    staff,
                    locale
                )
            }
            .also {
                commandhandler?.run {
                    CommandHandler.setup(
                        prefix,
                        defaultColor,
                        errorColor,
                        displayStaffCommandsInHelp
                    )
                }
            }

    class CommandHandlerOptions {

        lateinit var prefix: String
        var defaultColor: Color? = null
        var errorColor: Color = Color(204, 0, 0)
        var displayStaffCommandsInHelp: Boolean = false

    }

}
