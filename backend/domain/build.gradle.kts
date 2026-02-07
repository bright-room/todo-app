plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(projects.shared)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.yavi)
    implementation(libs.password4j)

    testImplementation(libs.kotlin.test.junit5)
    testImplementation(libs.junit.jupiter)
}

kotlin {
    val javaVersion = libs.versions.java.get()
    jvmToolchain(javaVersion.toInt())
}

tasks {
    test {
        useJUnitPlatform()
    }
}
