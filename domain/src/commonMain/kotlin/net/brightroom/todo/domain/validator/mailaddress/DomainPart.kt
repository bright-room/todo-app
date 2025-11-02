package net.brightroom.todo.domain.validator.mailaddress

import kotlin.jvm.JvmInline

@JvmInline
value class DomainPart(private val value: String) {
    fun validate() {
        require(value.isNotEmpty()) { "Domain part cannot be empty" }
        require(value.length <= 255) { "Domain part cannot be more than 255 characters" }
        require(value.contains(".")) { "Domain part must contain a period" }
        require(value.first() != '.') { "Local part cannot start with a period" }
        require(value.last() != '.') { "Local part cannot end with a period" }
        require(!value.contains("..")) { "Local part cannot contain two consecutive periods" }

        val domainParts = value.split(".")
        val tld = domainParts.last()
        require(tld.length >= 2) { "Top level domain must be at least 2 characters long" }

        require(value.matches(Regex("^[a-zA-Z0-9._-]+$"))) { "Invalid local part" }
    }

    operator fun invoke(): String = value

    override fun toString(): String = value
}
