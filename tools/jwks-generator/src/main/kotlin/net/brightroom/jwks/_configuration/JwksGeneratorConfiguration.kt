package net.brightroom.jwks._configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.nio.file.Path

@Configuration
class JwksGeneratorConfiguration {
    @Bean
    fun saveDir(
        @Value($$"${batch.baseDir}") baseDir: String,
        @Value($$"${batch.environment}") environment: String,
    ): Path =
        Path
            .of(baseDir, environment)
            .also { it.toFile().mkdirs() }
}
