package net.brightroom.todo._configuration.di

import io.ktor.server.application.Application
import io.ktor.server.plugins.di.annotations.Property
import io.ktor.server.plugins.di.dependencies

fun Application.configure(
    @Property("ktor.development") isDevelopment: Boolean,
) {
    dependencies {
    }
}
