package net.brightroom.todo.domain.model.content

/**
 * 説明
 */
@JvmInline
value class Description(
    private val value: String,
) {
    operator fun invoke(): String = value

    override fun toString(): String = value
}
