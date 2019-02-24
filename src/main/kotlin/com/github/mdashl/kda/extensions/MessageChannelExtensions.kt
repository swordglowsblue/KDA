package com.github.mdashl.kda.extensions

import com.github.mdashl.kda.builders.EmbedBuilder
import com.github.mdashl.kda.embed
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.requests.restaction.MessageAction

inline fun MessageChannel.send(content: String? = null, init: EmbedBuilder.() -> Unit): MessageAction =
    sendMessage(embed(init)).content(content)
