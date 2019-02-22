package com.github.mdashl.kda.commandhandler.commands

import com.github.mdashl.kda.KDA
import com.github.mdashl.kda.commandhandler.Command
import com.github.mdashl.kda.commandhandler.annotations.GeneralCommand
import com.github.mdashl.kda.extensions.i18n
import com.github.mdashl.kda.extensions.placeholder

object InviteCommand : Command() {

    override val aliases: List<String> = listOf("invite")
    override val description: String = "commandhandler.commands.invite.description".i18n()
    override val usage: String = "commandhandler.commands.invite.usage".i18n()

    private val INVITE_LINK by lazy { KDA.jda.applicationInfo.complete().getInviteUrl() }

    @GeneralCommand
    fun invite() {
        reply {
            description += "commandhandler.commands.invite.reply.description".i18n()
                .placeholder("link", INVITE_LINK)
        }
    }

}
