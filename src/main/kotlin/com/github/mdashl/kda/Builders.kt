package com.github.mdashl.kda

import com.github.mdashl.kda.builders.ClientBuilder
import com.github.mdashl.kda.builders.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.MessageEmbed

inline fun client(init: ClientBuilder.() -> Unit): JDA =
    ClientBuilder().apply(init).build()

inline fun embed(init: EmbedBuilder.() -> Unit): MessageEmbed =
    EmbedBuilder().apply(init).build()
