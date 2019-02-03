package com.github.mdashl.kda.extensions

import com.github.mdashl.kda.commandhandler.CommandHandler
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.hooks.ListenerAdapter

fun JDA.setupCommandHandler(options: CommandHandler.Options) {
    CommandHandler.setup(this, options)
}

inline fun <reified T : Event> JDA.handlerOf(crossinline action: (event: T) -> Unit) =
    addEventListener(
        object : ListenerAdapter() {
            override fun onGenericEvent(event: Event) {
                if (event is T) {
                    action(event)
                }
            }
        }
    )
