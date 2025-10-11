plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(ktorLib.plugins.ktor)
}

group = "net.brightroom"
version = "1.0.0"

dependencies {
    implementation(projects.shared)
    implementation(projects.backend.domain)
    implementation(projects.backend.infrastructure.schemas)

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines)

    implementation(ktorLib.serialization.kotlinx.json)

    implementation(ktorLib.server.core)
    implementation(ktorLib.server.cio)
    implementation(ktorLib.server.config.yaml)
    implementation(ktorLib.server.di)
    implementation(ktorLib.server.resources)
    implementation(ktorLib.server.statusPages)
    implementation(ktorLib.server.doubleReceive)
    implementation(ktorLib.server.callLogging)
    implementation(ktorLib.server.callId)
    implementation(ktorLib.server.contentNegotiation)

    implementation(libs.ktor.server.health)
    implementation(libs.ktor.openapi)
    implementation(libs.ktor.swagger.ui)
    implementation(libs.ktor.redoc)

    implementation(libs.schema.kenerator.core)
    implementation(libs.schema.kenerator.reflection)
    implementation(libs.schema.kenerator.serialization)
    implementation(libs.schema.kenerator.swagger)

    implementation(libs.exposed.core)
    implementation(libs.exposed.r2dbc)
    implementation(libs.exposed.kotlin.datetime)

    implementation(libs.r2dbc.postgresql)
    implementation(libs.r2dbc.pool)

    implementation(libs.yavi)

    implementation(libs.logback)

    testImplementation(libs.kotlin.test.junit5)
    testImplementation(libs.mockk)
    testImplementation(ktorLib.server.testHost)
    testImplementation(ktorLib.client.contentNegotiation)
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("net.brightroom.todo.ApplicationKt")
}

tasks {
    test {
        useJUnitPlatform {
            excludeTags("integration")
        }
    }

    val integrationTest by registering(Test::class) {
        group = "verification"

        testClassesDirs = sourceSets["test"].output.classesDirs
        classpath = sourceSets["test"].runtimeClasspath

        useJUnitPlatform {
            includeTags("integration")
        }

        shouldRunAfter(test)
    }

    check {
        dependsOn(integrationTest)
    }
}
