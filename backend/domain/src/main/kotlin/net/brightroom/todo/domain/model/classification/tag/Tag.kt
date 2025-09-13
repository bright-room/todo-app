package net.brightroom.todo.domain.model.classification.tag

/**
 * タグ
 */
@JvmInline
value class Tag(
    private val value: String,
) {
    operator fun invoke(): String = value

    override fun toString(): String = value
}
