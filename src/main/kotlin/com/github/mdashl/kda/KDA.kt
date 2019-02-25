package com.github.mdashl.kda

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.User
import java.util.*

object KDA {

    lateinit var client: JDA

    lateinit var ownerId: String
    lateinit var staffIds: List<String>
    lateinit var locale: Locale

    lateinit var messages: ResourceBundle

    val owner: User
        get() = client.getUserById(ownerId)

    fun setup(
        client: JDA,
        ownerId: String,
        staffIds: List<String>,
        locale: Locale
    ) {
        this.client = client
        this.ownerId = ownerId
        this.staffIds = staffIds
        this.locale = locale

        this.messages = ResourceBundle.getBundle("messages", locale)
    }

}
