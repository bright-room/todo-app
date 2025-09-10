package net.brightroom.migration.detector.domain.model.clazz

import net.brightroom.migration.detector.domain.model.clazz.dummy.AnnotatedClassDummyClass
import net.brightroom.migration.detector.domain.model.clazz.dummy.AnotherAnnotationDummyClass
import net.brightroom.migration.detector.domain.model.clazz.dummy.BaseClassDummyClass
import net.brightroom.migration.detector.domain.model.clazz.dummy.ClassWithDefaultConstructorDummyClass
import net.brightroom.migration.detector.domain.model.clazz.dummy.ClassWithoutDefaultConstructorDummyClass
import net.brightroom.migration.detector.domain.model.clazz.dummy.SimpleClassDummyClass
import net.brightroom.migration.detector.domain.model.clazz.dummy.TestAnnotationDummyClass
import net.brightroom.migration.detector.domain.model.clazz.dummy.TestObjectDummyClass
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("スキャンされたクラステスト")
class ScanClassTest {
    @Test
    fun `引数で渡されたアノテーションが付いているクラスの場合trueが返される`() {
        val scanClass = ScanClass(AnnotatedClassDummyClass::class)
        assertTrue(scanClass.hasAnnotation(TestAnnotationDummyClass::class))
    }

    @Test
    fun `引数で渡されたアノテーションが付いていないクラスの場合falseが返される`() {
        val scanClass = ScanClass(SimpleClassDummyClass::class)
        assertFalse(scanClass.hasAnnotation(TestAnnotationDummyClass::class))
    }

    @Test
    fun `引数で渡されたアノテーションと異なるアノテーションを指定した場合falseが返される`() {
        val scanClass = ScanClass(AnnotatedClassDummyClass::class)
        assertFalse(scanClass.hasAnnotation(AnotherAnnotationDummyClass::class))
    }

    @Test
    fun `引数で渡されたクラスをスーパークラスとして持っている場合trueが返される`() {
        val scanClass = ScanClass(AnnotatedClassDummyClass::class)
        assertTrue(scanClass.hasSuperclass(BaseClassDummyClass::class))
    }

    @Test
    fun `引数で渡されたクラスをスーパークラスとして持っていない場合falseが返される`() {
        val scanClass = ScanClass(SimpleClassDummyClass::class)
        assertFalse(scanClass.hasSuperclass(BaseClassDummyClass::class))
    }

    @Test
    fun `Anyクラスをスーパークラスとして指定した場合trueが返される`() {
        val scanClass = ScanClass(SimpleClassDummyClass::class)
        assertTrue(scanClass.hasSuperclass(Any::class))
    }

    @Test
    fun `objectクラスのインスタンスが正しく取得される`() {
        val scanClass = ScanClass(TestObjectDummyClass::class)
        assertEquals(TestObjectDummyClass, scanClass.toInstance<TestObjectDummyClass>())
    }

    @Test
    fun `デフォルトコンストラクタを持つクラスのインスタンスが作成される`() {
        val scanClass = ScanClass(ClassWithDefaultConstructorDummyClass::class)
        val instance = scanClass.toInstance<ClassWithDefaultConstructorDummyClass>()
        assertEquals("test", instance.value)
    }

    @Test
    fun `デフォルトコンストラクタがないクラスの場合例外が発生する`() {
        val scanClass = ScanClass(ClassWithoutDefaultConstructorDummyClass::class)
        assertThrows<Exception> {
            scanClass.toInstance<ClassWithoutDefaultConstructorDummyClass>()
        }
    }

    @Test
    fun `クラスの完全修飾名が文字列として返される`() {
        val scanClass = ScanClass(AnnotatedClassDummyClass::class)
        assertEquals("net.brightroom.migration.detector.domain.model.clazz.dummy.AnnotatedClassDummyClass", scanClass.toString())
    }

    @Test
    fun `匿名クラスの場合空文字列またはnull値が適切に処理される`() {
        val scanClass = ScanClass(object {}::class)
        assertEquals("", scanClass.toString())
    }

    @Test
    fun `hasAnnotationでエラーが発生した場合falseが返される`() {
        val scanClass = ScanClass(SimpleClassDummyClass::class)
        val result = scanClass.hasAnnotation(TestAnnotationDummyClass::class)
        assertFalse(result)
    }

    @Test
    fun `hasSuperclassでエラーが発生した場合falseが返される`() {
        val scanClass = ScanClass(SimpleClassDummyClass::class)
        val result = scanClass.hasSuperclass(BaseClassDummyClass::class)
        assertFalse(result)
    }
}
