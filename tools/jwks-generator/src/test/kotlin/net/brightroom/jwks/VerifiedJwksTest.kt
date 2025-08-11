package net.brightroom.jwks

import com.auth0.jwt.JWT
import net.brightroom.jwks.application.service.JwksService
import net.brightroom.jwks.application.service.KeyPairService
import net.brightroom.jwks.domain.model.Claims
import net.brightroom.jwks.infrastructure.CreateJwtToken
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("生成されたJwksのテスト")
class VerifiedJwksTest
    @Autowired
    constructor(
        private val keyPairService: KeyPairService,
        private val jwksService: JwksService,
        private val createJwtToken: CreateJwtToken,
    ) {
        @Test
        fun test() {
            val keyPair = keyPairService.load()
            val jwks = jwksService.load()

            val jwt = createJwtToken.create(keyPair, jwks)

            val decodedJWT = JWT.decode(jwt())
            val actual =
                mapOf<String, String>(
                    "name" to decodedJWT.getClaim("name").asString(),
                    "role" to decodedJWT.getClaim("role").asString(),
                    "email" to decodedJWT.getClaim("email").asString(),
                )

            assertEquals(Claims.create(), Claims(actual))
        }
    }
