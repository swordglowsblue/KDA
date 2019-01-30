package com.github.mdashl.kda.extensions

fun Array<out String>.containsIgnoreCase(element: String): Boolean = any { it.equals(element, true) }
