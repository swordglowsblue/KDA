package com.github.mdashl.kda.extensions

import com.github.mdashl.kda.KDA
import net.dv8tion.jda.api.entities.User

fun User.isOwner(): Boolean = KDA.ownerId == id

fun User.isStaff(): Boolean = id in KDA.staffIds
