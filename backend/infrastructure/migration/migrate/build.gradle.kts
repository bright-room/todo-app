plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

dependencies {
    implementation(projects.shared)
    implementation(projects.backend.infrastructure.schemas)
    implementation(projects.backend.infrastructure.migration.annotation)

    implementation(libs.spring.boot.starter)

    implementation(libs.kotlinx.datetime)

    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.migration)

    implementation(libs.flyway)
    implementation(libs.flyway.database.postgresql)

    runtimeOnly(libs.jdbc.postgresql)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

springBoot {
    mainClass.set("net.brightroom.migration.migrate.MigrateKt")
}

tasks {
    processTestResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    register<JavaExec>("generateMigrationScript") {
        group = "application"
        description = "Generate a migration script in the path src/main/resources/migration"
        classpath = sourceSets.main.get().runtimeClasspath
        mainClass = "net.brightroom.migration.generate_script.GenerateMigrationScriptKt"
    }
}
