package net.brightroom.migration.detector.domain.model.clazz

/**
 * スキャンを行うベースパッケージ
 */
internal class ScanBasePackage(
    private val value: String,
) {
    fun resolve(path: String): ScanBasePackage = ScanBasePackage("$value.$path")

    fun toPath(): String = value.replace(".", "/")

    override fun toString() = value
}
