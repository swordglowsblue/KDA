package com.github.mdashl.kda.extensions

import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.requests.restaction.MessageAction

fun TextChannel.sendMessage(content: String, embed: MessageEmbed): MessageAction =
    sendMessage(
        MessageBuilder()
            .setContent(content)
            .setEmbed(embed)
            .build()
    )
