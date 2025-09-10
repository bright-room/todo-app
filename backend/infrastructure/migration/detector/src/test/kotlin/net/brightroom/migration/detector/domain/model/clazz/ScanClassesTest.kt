package net.brightroom.migration.detector.domain.model.clazz

import net.brightroom.migration.detector.domain.model.clazz.dummy.TestClass1DummyClass
import net.brightroom.migration.detector.domain.model.clazz.dummy.TestClass2DummyClass
import net.brightroom.migration.detector.domain.model.clazz.dummy.TestClass3DummyClass
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("スキャンされたクラス一覧テスト")
class ScanClassesTest {
    @Test
    fun `条件に一致するクラスが正しく抽出される`() {
        val scanClasses =
            ScanClasses(
                listOf(
                    ScanClass(TestClass1DummyClass::class),
                    ScanClass(TestClass2DummyClass::class),
                    ScanClass(TestClass3DummyClass::class),
                ),
            )

        val actual = scanClasses.extract { it.toString().contains("TestClass1DummyClass") }
        val expected =
            ScanClasses(
                listOf(
                    ScanClass(TestClass1DummyClass::class),
                ),
            )
        assertEquals(expected, actual)
    }

    @Test
    fun `条件に一致するクラスが存在しない場合空のリストが返される`() {
        val scanClasses =
            ScanClasses(
                listOf(
                    ScanClass(TestClass1DummyClass::class),
                    ScanClass(TestClass2DummyClass::class),
                ),
            )

        val actual = scanClasses.extract { it.toString().contains("NonExistent") }
        val expected = ScanClasses.empty()
        assertEquals(expected, actual)
    }

    @Test
    fun `複数の条件に一致するクラスが正しく抽出される`() {
        val scanClasses =
            ScanClasses(
                listOf(
                    ScanClass(TestClass1DummyClass::class),
                    ScanClass(TestClass2DummyClass::class),
                    ScanClass(TestClass3DummyClass::class),
                ),
            )

        val actual = scanClasses.extract { it.toString().contains("TestClass") }
        assertEquals(scanClasses, actual)
    }

    @Test
    fun `2つのScanClassesが正しくマージされる`() {
        val scanClasses1 =
            ScanClasses(
                listOf(
                    ScanClass(TestClass1DummyClass::class),
                    ScanClass(TestClass2DummyClass::class),
                ),
            )

        val scanClasses2 =
            ScanClasses(
                listOf(
                    ScanClass(TestClass3DummyClass::class),
                ),
            )

        val actual = scanClasses1.merge(scanClasses2)
        val expected =
            ScanClasses(
                listOf(
                    ScanClass(TestClass1DummyClass::class),
                    ScanClass(TestClass2DummyClass::class),
                    ScanClass(TestClass3DummyClass::class),
                ),
            )
        assertEquals(expected, actual)
    }

    @Test
    fun `空のScanClassesとマージした場合元のリストが保持される`() {
        val scanClasses1 =
            ScanClasses(
                listOf(
                    ScanClass(TestClass1DummyClass::class),
                    ScanClass(TestClass2DummyClass::class),
                ),
            )

        val scanClasses2 = ScanClasses.empty()

        val actual = scanClasses1.merge(scanClasses2)
        val expected =
            ScanClasses(
                listOf(
                    ScanClass(TestClass1DummyClass::class),
                    ScanClass(TestClass2DummyClass::class),
                ),
            )
        assertEquals(expected, actual)
    }

    @Test
    fun `同じクラスを含むScanClassesをマージした場合重複が保持される`() {
        val scanClasses1 =
            ScanClasses(
                listOf(
                    ScanClass(TestClass1DummyClass::class),
                    ScanClass(TestClass2DummyClass::class),
                ),
            )

        val scanClasses2 =
            ScanClasses(
                listOf(
                    ScanClass(TestClass1DummyClass::class),
                    ScanClass(TestClass2DummyClass::class),
                ),
            )

        val actual = scanClasses1.merge(scanClasses2)
        val expected =
            ScanClasses(
                listOf(
                    ScanClass(TestClass1DummyClass::class),
                    ScanClass(TestClass2DummyClass::class),
                ),
            )
        assertEquals(expected, actual)
    }
}
