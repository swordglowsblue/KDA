package com.github.mdashl.kda.commandhandler

abstract class StaffCommand : Command() {
    override fun checkPermission(): Boolean =
        member.user.id == CommandHandler.options.ownerId || member.user.id in CommandHandler.options.staff
}
