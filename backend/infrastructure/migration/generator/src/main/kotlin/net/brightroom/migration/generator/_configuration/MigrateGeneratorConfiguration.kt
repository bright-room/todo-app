package net.brightroom.migration.generator._configuration

import net.brightroom.migration.generator.domain.model.OutputDirectory
import net.brightroom.migration.generator.domain.model.ScriptName
import net.brightroom.migration.generator.domain.model.Version
import org.jetbrains.exposed.v1.spring.boot.autoconfigure.ExposedAutoConfiguration
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ImportAutoConfiguration(
    value = [ExposedAutoConfiguration::class],
    exclude = [DataSourceTransactionManagerAutoConfiguration::class],
)
class MigrateGeneratorConfiguration(
    private val migrationGeneratorProperties: MigrationGeneratorProperties,
) {
    @Bean
    fun outputDirectory(): OutputDirectory {
        val directory = migrationGeneratorProperties.outputDirectory
        return OutputDirectory(directory)
    }

    @Bean
    fun scriptName(): ScriptName {
        val suffix = migrationGeneratorProperties.filenameSuffix
        return ScriptName.create(Version(), suffix)
    }
}
