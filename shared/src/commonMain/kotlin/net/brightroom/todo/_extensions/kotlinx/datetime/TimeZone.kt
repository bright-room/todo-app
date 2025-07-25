package net.brightroom.todo._extensions.kotlinx.datetime

import kotlinx.datetime.TimeZone

val TimeZone.Companion.JST: TimeZone
    get() = TimeZone.of("Asia/Tokyo")
