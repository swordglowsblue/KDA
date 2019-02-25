package com.github.mdashl.kda.commandhandler

import com.github.mdashl.kda.extensions.isOwner

abstract class OwnerCommand : StaffCommand() {

    override fun checkAccess(): Boolean = member.user.isOwner()

}
