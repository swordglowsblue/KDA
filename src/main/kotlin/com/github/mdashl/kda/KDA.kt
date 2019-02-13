package com.github.mdashl.kda

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.User
import java.util.*

object KDA {

    lateinit var jda: JDA

    lateinit var ownerId: String
    lateinit var staffIds: List<String>
    lateinit var locale: Locale

    lateinit var MESSAGES: ResourceBundle

    val owner: User
        get() = jda.getUserById(ownerId)

    fun setup(
        jda: JDA,
        ownerId: String,
        staffIds: List<String>,
        locale: Locale
    ) {
        this.jda = jda
        this.ownerId = ownerId
        this.staffIds = staffIds
        this.locale = locale

        this.MESSAGES = ResourceBundle.getBundle("messages", locale)
    }

}
