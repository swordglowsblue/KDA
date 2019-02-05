package com.github.mdashl.kda.commandhandler

import com.github.mdashl.kda.KDA

abstract class StaffCommand : Command() {
    override fun checkPermission(): Boolean =
        member.user.id == KDA.owner.id || member.user.id in KDA.staff
}
