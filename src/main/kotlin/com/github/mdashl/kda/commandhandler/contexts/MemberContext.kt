package com.github.mdashl.kda.commandhandler.contexts

import com.github.mdashl.kda.commandhandler.CommandContext
import com.github.mdashl.kda.extensions.i18n
import com.github.mdashl.kda.extensions.isLong
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message

object MemberContext : CommandContext<Member>(Member::class.java) {

    override fun handle(message: Message, text: String, arg: String): Member =
        message.mentionedMembers.firstOrNull()
            ?: arg.takeIf(String::isLong)?.let { message.guild.getMemberById(it) }
            ?: message.guild.getMembersByEffectiveName(arg, true).firstOrNull()
            ?: message.guild.getMembersByEffectiveName(text, true).firstOrNull()
            ?: throw IllegalArgumentException("commandhandler.contexts.member.error".i18n())

}
