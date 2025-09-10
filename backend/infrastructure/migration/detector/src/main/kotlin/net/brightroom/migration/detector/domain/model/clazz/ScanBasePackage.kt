package net.brightroom.migration.detector.domain.model.clazz

/**
 * スキャンを行うベースパッケージ
 */
class ScanBasePackage(
    private val value: String,
) {
    init {
        require(value.isNotBlank()) { "value must not be blank" }
    }

    fun resolve(path: String): ScanBasePackage {
        if (path.isBlank()) throw IllegalArgumentException("value must not be blank")
        return ScanBasePackage("$value.$path")
    }

    fun toPath(): String = value.replace(".", "/")

    override fun toString() = value
}
