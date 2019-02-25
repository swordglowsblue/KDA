package com.github.mdashl.kda.commandhandler.commands

import com.github.mdashl.kda.KDA
import com.github.mdashl.kda.commandhandler.Command
import com.github.mdashl.kda.commandhandler.annotations.GeneralCommand
import com.github.mdashl.kda.extensions.i18n

object InviteCommand : Command() {

    override val aliases: List<String> = listOf("invite")
    override val description: String = "commandhandler.commands.invite.description".i18n()
    override val usage: String = "commandhandler.commands.invite.usage".i18n()

    private lateinit var INVITE_LINK: String

    init {
        KDA.client.retrieveApplicationInfo().queue {
            INVITE_LINK = it.getInviteUrl()
        }
    }

    @GeneralCommand
    fun invite() {
        reply {
            description += "commandhandler.commands.invite.reply.description".i18n(INVITE_LINK)
        }
    }

}
