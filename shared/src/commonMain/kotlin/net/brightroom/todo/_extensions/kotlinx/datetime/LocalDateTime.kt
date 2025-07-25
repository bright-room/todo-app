@file:OptIn(ExperimentalTime::class)

package net.brightroom.todo._extensions.kotlinx.datetime

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

fun LocalDateTime.Companion.now(timeZone: TimeZone = TimeZone.JST): LocalDateTime = Clock.System.now().toLocalDateTime(timeZone)
