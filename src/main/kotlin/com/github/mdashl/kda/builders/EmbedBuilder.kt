package com.github.mdashl.kda.builders

import net.dv8tion.jda.api.EmbedBuilder.ZERO_WIDTH_SPACE
import net.dv8tion.jda.api.entities.EmbedType
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.Role
import java.awt.Color
import java.time.OffsetDateTime

class EmbedBuilder {

    private var url: String? = null
    private var title: String? = null
    private val description: StringBuilder = StringBuilder()
    private var timestamp: OffsetDateTime? = null
    private var color: Color? = null
    private var thumbnail: String? = null
    private var author: Author? = null
    private var footer: Footer? = null
    private var image: String? = null
    private val fields: ArrayList<Field> = ArrayList()

    fun url(url: String) {
        this.url = url
    }

    fun title(title: String) {
        this.title = title
    }

    fun description(description: String) {
        this.description.appendln(description)
    }

    fun timestamp(timestamp: OffsetDateTime) {
        this.timestamp = timestamp
    }

    fun color(color: Color) {
        this.color = color
    }

    fun color(red: Int, green: Int, blue: Int) {
        color(Color(red, green, blue))
    }

    fun thumbnail(thumbnail: String) {
        this.thumbnail = thumbnail
    }

    fun author(init: Author.() -> Unit) {
        this.author = Author().apply(init)
    }

    fun footer(init: Footer.() -> Unit) {
        this.footer = Footer().apply(init)
    }

    fun image(image: String) {
        this.image = image
    }

    fun field(init: Field.() -> Unit) {
        this.fields += Field().apply(init)
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

        private var name: String? = null
        private var url: String? = null
        private var icon: String? = null

        fun name(name: String) {
            this.name = name
        }

        fun url(url: String) {
            this.url = url
        }

        fun icon(icon: String) {
            this.icon = icon
        }

        fun build(): MessageEmbed.AuthorInfo = MessageEmbed.AuthorInfo(name, url, icon, null)

    }

    class Footer {

        private var text: String? = null
        private var icon: String? = null

        fun text(text: String) {
            this.text = text
        }

        fun icon(icon: String) {
            this.icon = icon
        }

        fun build() = MessageEmbed.Footer(text, icon, null)

    }

    class Field {

        private var name: String = ZERO_WIDTH_SPACE
        private var value: String = ZERO_WIDTH_SPACE
        private var inline: Boolean = true

        fun name(name: String) {
            this.name = name
        }

        fun value(value: String) {
            this.value = value
        }

        fun inline(inline: Boolean) {
            this.inline = inline
        }

        fun build(): MessageEmbed.Field = MessageEmbed.Field(name, value, inline)

    }

}
