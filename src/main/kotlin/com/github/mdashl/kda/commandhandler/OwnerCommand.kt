package com.github.mdashl.kda.commandhandler

import com.github.mdashl.kda.KDA

abstract class OwnerCommand : StaffCommand() {

    override fun checkPermission(): Boolean = member.user.id == KDA.owner.id

}
