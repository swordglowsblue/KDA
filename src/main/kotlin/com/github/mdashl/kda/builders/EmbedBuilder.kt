package com.github.mdashl.kda.builders

import net.dv8tion.jda.api.EmbedBuilder.ZERO_WIDTH_SPACE
import net.dv8tion.jda.api.entities.EmbedType
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.Role
import java.awt.Color
import java.time.OffsetDateTime

class EmbedBuilder {

    var title: String? = null
    val description: StringBuilder = StringBuilder()
    var url: String? = null
    var timestamp: OffsetDateTime? = null
    var color: Color? = null
    var thumbnail: String? = null
    var author: Author? = null
    var footer: Footer? = null
    var image: String? = null
    val fields: ArrayList<Field> = ArrayList()

    operator fun StringBuilder.plusAssign(string: String) {
        appendln(string)
    }

    inline fun author(init: Author.() -> Unit) {
        author = Author().apply(init)
    }

    inline fun footer(init: Footer.() -> Unit) {
        footer = Footer().apply(init)
    }

    inline fun field(init: Field.() -> Unit) {
        fields += Field().apply(init)
    }

    fun build(): MessageEmbed =
        MessageEmbed(
            url,
            title,
            description.toString(),
            EmbedType.RICH,
            timestamp,
            color?.rgb ?: Role.DEFAULT_COLOR_RAW,
            thumbnail?.let { MessageEmbed.Thumbnail(it, null, 0, 0) },
            null,
            author?.build(),
            null,
            footer?.build(),
            image?.let { MessageEmbed.ImageInfo(it, null, 0, 0) },
            fields.map(Field::build)
        )

    class Author {

        var name: String? = null
        var url: String? = null
        var icon: String? = null

        fun build(): MessageEmbed.AuthorInfo = MessageEmbed.AuthorInfo(name, url, icon, null)

    }

    class Footer {

        var text: String? = null
        var icon: String? = null

        fun build(): MessageEmbed.Footer = MessageEmbed.Footer(text, icon, null)

    }

    class Field {

        var name: String = ZERO_WIDTH_SPACE
        var value: String = ZERO_WIDTH_SPACE
        var inline: Boolean = true

        fun build(): MessageEmbed.Field = MessageEmbed.Field(name, value, inline)

    }

}
