package com.github.mdashl.kda

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.User
import java.util.*

object KDA {
    val MESSAGES: ResourceBundle = ResourceBundle.getBundle("messages", locale)

    lateinit var jda: JDA

    lateinit var owner: User
    lateinit var staff: List<String>
    lateinit var locale: Locale

    fun setup(jda: JDA, options: Options) {
        this.jda = jda

        this.owner = jda.getUserById(options.owner)
        this.staff = options.staff
        this.locale = options.locale
    }

    data class Options(
        val owner: String,
        val staff: List<String> = emptyList(),
        val locale: Locale = Locale.US
    )
}
