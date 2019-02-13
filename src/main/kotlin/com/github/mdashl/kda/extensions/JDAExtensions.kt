package com.github.mdashl.kda.extensions

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

inline fun <reified T : Event> JDA.handlerOf(crossinline action: (event: T) -> Unit) {
    addEventListener(
        object : ListenerAdapter() {

            override fun onGenericEvent(event: GenericEvent) {
                if (event is T) {
                    action(event)
                }
            }

        }
    )
}
