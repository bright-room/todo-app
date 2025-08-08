package net.brightroom.migration

import org.jetbrains.exposed.v1.core.Table

internal data class OrderedTable(
    val table: Table,
    val order: Int,
) : Comparable<OrderedTable> {
    override fun compareTo(other: OrderedTable): Int = this.order.compareTo(other.order)
}
