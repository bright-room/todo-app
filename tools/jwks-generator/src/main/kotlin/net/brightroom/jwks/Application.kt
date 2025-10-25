package net.brightroom.jwks

import net.brightroom.jwks.application.service.CreateJwksService
import net.brightroom.jwks.application.service.CreateKeyPairService
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application(private val createKeyPairService: CreateKeyPairService, private val createJwksService: CreateJwksService) :
    ApplicationRunner {
    private val log = LoggerFactory.getLogger(Application::class.java)

    override fun run(args: ApplicationArguments?) {
        try {
            val keyPair = createKeyPairService.create()
            createJwksService.create(keyPair)
        } catch (e: Exception) {
            log.error("jwksの生成でエラー", e)
            throw e
        }
    }
}

fun main(args: Array<String>) = runApplication<Application>(*args).close()
