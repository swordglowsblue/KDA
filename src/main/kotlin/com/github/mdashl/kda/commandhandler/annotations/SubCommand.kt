package com.github.mdashl.kda.commandhandler.annotations

@Target(AnnotationTarget.FUNCTION)
@Retention
annotation class SubCommand(vararg val value: String)
