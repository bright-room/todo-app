package net.brightroom.todo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
