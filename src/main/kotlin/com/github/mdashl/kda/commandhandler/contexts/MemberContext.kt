package com.github.mdashl.kda.commandhandler.contexts

import com.github.mdashl.kda.commandhandler.CommandContext
import com.github.mdashl.kda.extensions.isLong
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message

object MemberContext : CommandContext<Member>(Member::class.java) {
    override fun handle(message: Message, arg: String): Member =
        message.mentionedMembers.takeIf { it.isNotEmpty() }?.first()
            ?: arg.takeIf { it.isLong() }?.let { message.guild.getMemberById(it) }
            ?: message.guild.getMembersByEffectiveName(arg, true)?.takeIf { it.isNotEmpty() }?.first()
            ?: throw IllegalArgumentException("Member does not exist")
}