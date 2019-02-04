package com.github.mdashl.kda.commandhandler

abstract class StaffCommand : Command() {
    override fun checkPermission(): Boolean = member.user.id in CommandHandler.options.staff
}
