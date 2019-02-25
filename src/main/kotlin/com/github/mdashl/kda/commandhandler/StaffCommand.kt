package com.github.mdashl.kda.commandhandler

import com.github.mdashl.kda.extensions.isOwner
import com.github.mdashl.kda.extensions.isStaff

abstract class StaffCommand : Command() {

    override val displayInHelp: Boolean = CommandHandler.displayStaffCommandsInHelp

    override fun checkAccess(): Boolean = member.user.isOwner() || member.user.isStaff()

}
