package com.github.mdashl.kda.extensions

fun List<String>.containsIgnoreCase(element: String): Boolean = any { it.equals(element, true) }
