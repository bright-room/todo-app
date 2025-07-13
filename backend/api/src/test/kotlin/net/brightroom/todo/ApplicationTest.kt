package net.brightroom.todo

import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import org.junit.Assert.assertEquals
import kotlin.test.Test

class ApplicationTest {
    @Test
    fun testRoot() =
        testApplication {
            application {
                module()
            }
            val response = client.get("/")
            assertEquals(HttpStatusCode.OK, response.status)
            assertEquals("Ktor: ${Greeting().greet()}", response.bodyAsText())
        }
}
