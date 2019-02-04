package com.github.mdashl.kda.commandhandler

abstract class OwnerCommand : Command() {
    override fun checkPermission(): Boolean = member.user.id == CommandHandler.options.ownerId
}
