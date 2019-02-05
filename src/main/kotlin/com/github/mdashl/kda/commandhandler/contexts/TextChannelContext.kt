package com.github.mdashl.kda.commandhandler.contexts

import com.github.mdashl.kda.commandhandler.CommandContext
import com.github.mdashl.kda.extensions.i18n
import com.github.mdashl.kda.extensions.isLong
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.TextChannel

object TextChannelContext : CommandContext<TextChannel>(TextChannel::class.java) {
    override fun handle(message: Message, arg: String): TextChannel =
        message.mentionedChannels.firstOrNull()
            ?: arg.takeIf { it.isLong() }?.let { message.guild.getTextChannelById(it) }
            ?: message.guild.getTextChannelsByName(arg, true).firstOrNull()
            ?: throw IllegalArgumentException("commandhandler.contexts.textchannel.error".i18n())
}
