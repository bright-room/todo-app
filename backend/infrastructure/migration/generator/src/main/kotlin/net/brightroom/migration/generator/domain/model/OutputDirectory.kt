package net.brightroom.migration.generator.domain.model

/**
 * 出力先ディレクトリ
 */
class OutputDirectory(private val value: String) {
    operator fun invoke(): String = value

    override fun toString(): String = value
}
