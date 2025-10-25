package net.brightroom.migration.generator.domain.model

/**
 * スクリプトファイル名
 */
class ScriptName private constructor(private val value: String) {
    operator fun invoke(): String = value

    override fun toString(): String = value

    companion object {
        fun create(
            version: Version,
            scriptSuffix: String,
        ): ScriptName = ScriptName("${version.asText()}__$scriptSuffix")
    }
}
