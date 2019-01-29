package com.github.mdashl.kda

import com.github.mdashl.kda.builders.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.MessageEmbed

inline fun jda(token: String, init: JDABuilder.() -> Unit = {}): JDA =
    JDABuilder(token).apply(init).build().awaitReady()

inline fun embed(init: EmbedBuilder.() -> Unit): MessageEmbed =
    EmbedBuilder().apply(init).build()
