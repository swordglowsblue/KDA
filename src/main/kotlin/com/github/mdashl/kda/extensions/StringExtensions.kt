package com.github.mdashl.kda.extensions

import com.github.mdashl.kda.KDA

val SPACE_PATTERN = " +".toRegex()

fun String.removeDoubleSpaces(): String = replace(SPACE_PATTERN, " ")

fun String.isInt(): Boolean =
    try {
        toInt()
        true
    } catch (exception: NumberFormatException) {
        false
    }

fun String.isLong(): Boolean =
    try {
        toLong()
        true
    } catch (exception: NumberFormatException) {
        false
    }

fun String.isBoolean(): Boolean =
    equals("true", true) || equals("false", true)

internal fun String.i18n(): String = KDA.MESSAGES.getString(this)

fun String.placeholder(name: String, value: String): String = replace("%%$name%%", value)
