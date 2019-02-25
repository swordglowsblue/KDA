package com.github.mdashl.kda.commandhandler.commands

import com.github.mdashl.kda.KDA
import com.github.mdashl.kda.commandhandler.OwnerCommand
import com.github.mdashl.kda.commandhandler.annotations.GeneralCommand
import com.github.mdashl.kda.extensions.i18n

object RestartCommand : OwnerCommand() {

    override val aliases: List<String> = listOf("restart")
    override val description: String = "commandhandler.commands.restart.description".i18n()
    override val usage: String = "commandhandler.commands.restart.usage".i18n()

    @GeneralCommand
    fun restart() {
        KDA.client.shutdown()
        System.exit(0)
    }

}
