package com.github.mdashl.kda

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.User
import java.util.*

object KDA {

    lateinit var MESSAGES: ResourceBundle
        private set

    lateinit var jda: JDA
        private set

    lateinit var owner: User
        private set
    lateinit var staff: List<String>
        private set
    lateinit var locale: Locale
        private set

    fun setup(jda: JDA, options: Options) {
        this.jda = jda

        this.owner = jda.getUserById(options.owner)
        this.staff = options.staff
        this.locale = options.locale

        this.MESSAGES = ResourceBundle.getBundle("messages", locale)
    }

    data class Options(
        val owner: String,
        val staff: List<String> = emptyList(),
        val locale: Locale = Locale.US
    )

}
