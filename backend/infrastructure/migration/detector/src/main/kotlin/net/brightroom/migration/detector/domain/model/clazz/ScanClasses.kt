package net.brightroom.migration.detector.domain.model.clazz

data class ScanClasses(
    val list: List<ScanClass>,
) {
    fun extract(predicate: (ScanClass) -> Boolean): ScanClasses = ScanClasses(list.filter { predicate.invoke(it) })

    fun merge(other: ScanClasses): ScanClasses = ScanClasses(list + other.list)

    operator fun invoke() = list

    override fun toString() = list.joinToString(",") { it.toString() }

    companion object {
        fun empty(): ScanClasses = ScanClasses(emptyList())
    }
}
