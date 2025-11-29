package net.brightroom.todo.domain.model.label.content

import kotlin.jvm.JvmInline

/**
 * ラベルカラー
 */
@JvmInline
value class Color(private val value: String) {
    init {
        require(value.isNotEmpty()) { "Color cannot be empty" }
        require(value.length == 7) { "Color must be 7 characters long" }
        require(value.matches(Regex("^#[0-9a-fA-F].+$"))) { "Invalid color value" }
    }

    operator fun invoke(): String = value

    override fun toString(): String = value
}
