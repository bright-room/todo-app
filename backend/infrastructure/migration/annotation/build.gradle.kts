plugins {
    alias(libs.plugins.kotlin.jvm)
}

kotlin {
    val javaVersion = libs.versions.java.get()
    jvmToolchain(javaVersion.toInt())
}
