@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
    versionCatalogs {
        create("ktorLib") {
            from("io.ktor:ktor-version-catalog:3.2.3")
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "todo-app"

include(":shared")

include(":backend:domain")

include(":backend:infrastructure:schemas")
include(":backend:infrastructure:migration:annotation")
include(":backend:infrastructure:migration:migrate")

include(":backend:application:api")
include(":backend:application:scheduler")

include(":tools:jwks-generator")
