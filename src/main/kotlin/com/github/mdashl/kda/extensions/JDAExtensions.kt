package com.github.mdashl.kda.extensions

import com.github.mdashl.kda.KDA
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.hooks.ListenerAdapter

fun JDA.setupKDA(options: KDA.Options) {
    KDA.setup(this, options)
}

inline fun <reified T : Event> JDA.handlerOf(crossinline action: (event: T) -> Unit) {
    addEventListener(
        object : ListenerAdapter() {
            override fun onGenericEvent(event: Event) {
                if (event is T) {
                    action(event)
                }
            }
        }
    )
}
