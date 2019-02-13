package com.github.mdashl.kda.commandhandler

import com.github.mdashl.kda.extensions.isOwner

abstract class OwnerCommand : StaffCommand() {

    override fun checkPermission(): Boolean = member.user.isOwner()

}
