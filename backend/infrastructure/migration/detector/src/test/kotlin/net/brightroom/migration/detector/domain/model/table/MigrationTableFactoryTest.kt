package net.brightroom.migration.detector.domain.model.table

import net.brightroom.migration.detector.Migratable
import net.brightroom.migration.detector.domain.model.clazz.ScanClass
import net.brightroom.migration.detector.domain.model.clazz.ScanClasses
import net.brightroom.migration.detector.domain.model.table.dummy.AnnotatedNonClass
import net.brightroom.migration.detector.domain.model.table.dummy.NonAnnotatedNonClass
import net.brightroom.migration.detector.domain.model.table.dummy.NonMigratableTable
import net.brightroom.migration.detector.domain.model.table.dummy.TestTable1
import net.brightroom.migration.detector.domain.model.table.dummy.TestTable2
import org.jetbrains.exposed.v1.core.Table
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("マイグレーションテーブル一覧の生成テスト")
class MigrationTableFactoryTest {
    @Test
    fun `Migratableアノテーション付きのTableクラスを継承したクラスが正しく抽出される`() {
        val scanClasses =
            ScanClasses(
                listOf(
                    ScanClass(TestTable1::class),
                    ScanClass(TestTable2::class),
                    ScanClass(NonMigratableTable::class),
                    ScanClass(AnnotatedNonClass::class),
                    ScanClass(NonAnnotatedNonClass::class),
                ),
            )

        val actual = MigrationTableFactory.create(scanClasses)
        val expected =
            MigrationTables(
                listOf(
                    TestTable1,
                    TestTable2,
                ),
            )
        assertEquals(expected, actual)
    }

    @Test
    fun `Migratableアノテーションがないテーブルは除外される`() {
        val scanClasses =
            ScanClasses(
                listOf(
                    ScanClass(TestTable1::class),
                    ScanClass(NonMigratableTable::class),
                ),
            )

        val actual = MigrationTableFactory.create(scanClasses)
        val expected =
            MigrationTables(
                listOf(
                    TestTable1,
                ),
            )
        assertEquals(expected, actual)
    }

    @Test
    fun `Tableを継承していないクラスは除外される`() {
        val scanClasses =
            ScanClasses(
                listOf(
                    ScanClass(TestTable1::class),
                    ScanClass(AnnotatedNonClass::class),
                ),
            )

        val actual = MigrationTableFactory.create(scanClasses)
        val expected =
            MigrationTables(
                listOf(
                    TestTable1,
                ),
            )
        assertEquals(expected, actual)
    }

    @Test
    fun `条件に一致するクラスがない場合空のMigrationTablesが返される`() {
        val scanClasses =
            ScanClasses(
                listOf(
                    ScanClass(NonMigratableTable::class),
                    ScanClass(AnnotatedNonClass::class),
                    ScanClass(NonAnnotatedNonClass::class),
                ),
            )
        val actual = MigrationTableFactory.create(scanClasses)
        assertTrue(actual.toArray().isEmpty())
    }

    @Test
    fun `空のScanClassesから空のMigrationTablesが作成される`() {
        val scanClasses = ScanClasses.empty()
        val actual = MigrationTableFactory.create(scanClasses)
        assertTrue(actual.toArray().isEmpty())
    }

    @Test
    fun `インスタンス作成に失敗したクラスは除外される`() {
        // テスト用のクラス（インスタンス作成が困難なもの）
        @Migratable
        abstract class AbstractTable : Table("abstract_table") {
            val id = integer("id")
        }

        val scanClasses =
            ScanClasses(
                listOf(
                    ScanClass(TestTable1::class),
                    ScanClass(AbstractTable::class),
                ),
            )

        val actual = MigrationTableFactory.create(scanClasses)
        val expected =
            MigrationTables(
                listOf(
                    TestTable1,
                ),
            )

        // AbstractTableはインスタンス作成に失敗するため除外される
        assertEquals(expected, actual)
    }

    @Test
    fun `MigrationTablesの型が正しく返される`() {
        val scanClasses = ScanClasses(listOf(ScanClass(TestTable1::class)))
        val actual = MigrationTableFactory.create(scanClasses)
        assertTrue(actual.toArray().all { it is Table })
    }
}
