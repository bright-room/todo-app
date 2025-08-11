package net.brightroom.jwks

import net.brightroom.jwks.application.service.CreateJwksService
import net.brightroom.jwks.application.service.CreateKeyPairService
import net.brightroom.jwks.domain.model.PrivateKey
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

import java.util.*

//class JWTManager() {
//
//    /**
//     * JWTを生成
//     */
//    fun createJWT(
//        privateKey: PrivateKey,
//        keyId: String,
//        subject: String,
//        claims: Map<String, Any> = emptyMap(),
//        expirationHours: Long = 1
//    ): String {
//        val now = Instant.now()
//        val expiration = now.plus(expirationHours, ChronoUnit.HOURS)
//
//        val builder = Jwts.builder()
//            .setSubject(subject)
//            .setIssuedAt(Date.from(now))
//            .setExpiration(Date.from(expiration))
//            .setHeaderParam("kid", keyId)
//
//        // カスタムクレームを追加
//        claims.forEach { (key, value) ->
//            builder.claim(key, value)
//        }
//
//        return builder
//            .signWith(privateKey(), Jwts.SIG.RS256)
//            .compact()
//    }
//
//    /**
//     * JWTを検証・デコード
//     */
//    fun verifyAndDecodeJWT(token: String, publicKey: PublicKey): Map<String, Any> {
//        val claims = Jwts.parser()
//            .setSigningKey(publicKey())
//            .build()
//            .parseClaimsJws(token)
//            .body
//
//        return claims.mapKeys { it.key }.mapValues { it.value }
//    }
//}

@SpringBootApplication
class Application(
    private val createKeyPairService: CreateKeyPairService,
    private val createJwksService: CreateJwksService
) : ApplicationRunner {

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

//fun main() {
//
//    val keyManager = RS256KeyManager()
//
//    try {
//        // JWT作成・検証の例
//        println("\n=== JWT作成・検証例 ===")
//
//        // JWT作成
//        val claims = mapOf(
//            "name" to "John Doe",
//            "role" to "admin",
//            "email" to "john.doe@example.com"
//        )
//
//        val token = jwtManager.createJWT(
//            privateKey = keyPair.privateKey,
//            keyId = keyId,
//            subject = "1234567890",
//            claims = claims,
//            expirationHours = 1
//        )
//
//        println("生成されたJWT:")
//        println(token)
//
//        // JWT検証
//        val decodedClaims = jwtManager.verifyAndDecodeJWT(token, keyPair.publicKey)
//        println("\n検証・デコード結果:")
//        decodedClaims.forEach { (key, value) ->
//            println("  $key: $value")
//        }
//
//        // 読み込んだ鍵でJWT作成・検証
//        val testToken = jwtManager.createJWT(
//            privateKey = loadedPrivateKey,
//            keyId = loadedJwks.keys.first().keyId,
//            subject = "test-user",
//            claims = mapOf("test" to "success")
//        )
//
//        val testClaims = jwtManager.verifyAndDecodeJWT(testToken, loadedPublicKey)
//        println("ファイルから読み込んだ鍵での検証成功:")
//        println("  subject: ${testClaims["sub"]}")
//        println("  test: ${testClaims["test"]}")
//
//    } catch (e: Exception) {
//        println("エラーが発生しました: ${e.message}")
//        e.printStackTrace()
//    }
//}
