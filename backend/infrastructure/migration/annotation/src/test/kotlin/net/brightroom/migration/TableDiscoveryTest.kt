package net.brightroom.migration

import org.junit.Test
import kotlin.test.assertEquals

class TableDiscoveryTest {
    @Test
    fun test() {
        val tables = TableDiscovery.findAnnotatedTableObjects("net.brightroom.migration")

        assertEquals(2, tables.size)
        assertEquals("migration_test_1", tables[0].tableName)
        assertEquals("migration_test_2", tables[1].tableName)
    }
}
