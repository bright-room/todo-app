package net.brightroom.migration.detector.domain.model.clazz

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("スキャンを行うベースパッケージテスト")
class ScanBasePackageTest {
    @Test
    fun `パッケージのパスが正しく解決される`() {
        val basePackage = "com.example"
        val scanBasePackage = ScanBasePackage(basePackage)
        val subPath = "service"

        val result = scanBasePackage.resolve(subPath)
        assertEquals("com.example.service", result.toString())
    }

    @Test
    fun `複数階層のパッケージパスが正しく解決される`() {
        val basePackage = "com.example"
        val scanBasePackage = ScanBasePackage(basePackage)
        val subPath = "service.impl"

        val result = scanBasePackage.resolve(subPath)
        assertEquals("com.example.service.impl", result.toString())
    }

    @Test
    fun `パッケージ名がファイルパス形式に正しく変換される`() {
        val packageName = "com.example.test.service"
        val scanBasePackage = ScanBasePackage(packageName)

        val result = scanBasePackage.toPath()
        assertEquals("com/example/test/service", result)
    }

    @Test
    fun `単一パッケージ名がファイルパス形式に正しく変換される`() {
        val packageName = "test"
        val scanBasePackage = ScanBasePackage(packageName)

        val result = scanBasePackage.toPath()
        assertEquals("test", result)
    }

    @Test
    fun `空文字列のパッケージ名を生成する場合エラーになる`() {
        assertThrows<IllegalArgumentException> { ScanBasePackage("") }
    }

    @Test
    fun `パッケージ名に空文字列でパスを解決しようとした場合エラーになる`() {
        val basePackage = "com.example"
        val scanBasePackage = ScanBasePackage(basePackage)

        assertThrows<IllegalArgumentException> { scanBasePackage.resolve("") }
    }
}
