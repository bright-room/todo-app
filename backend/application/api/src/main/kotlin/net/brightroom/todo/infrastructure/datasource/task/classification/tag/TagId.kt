package net.brightroom.todo.infrastructure.datasource.task.classification.tag

@JvmInline
value class TagId(
    private val value: Int,
) {
    operator fun invoke(): Int = value

    override fun toString(): String = value.toString()
}
