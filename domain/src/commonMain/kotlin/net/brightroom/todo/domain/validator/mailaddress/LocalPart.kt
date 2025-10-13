package net.brightroom.todo.domain.validator.mailaddress

import kotlin.jvm.JvmInline

@JvmInline
value class LocalPart(private val value: String) {
    fun validate() {
        require(value.isNotEmpty()) { "Local part cannot be empty" }
        require(value.length <= 64) { "Local part cannot be more than 64 characters" }
        require(value.first() != '.') { "Local part cannot start with a period" }
        require(value.last() != '.') { "Local part cannot end with a period" }
        require(!value.contains("..")) { "Local part cannot contain two consecutive periods" }
        require(value.matches(Regex("^[a-zA-Z0-9._-]+$"))) { "Invalid local part" }
    }

    operator fun invoke(): String = value

    override fun toString(): String = value
}
