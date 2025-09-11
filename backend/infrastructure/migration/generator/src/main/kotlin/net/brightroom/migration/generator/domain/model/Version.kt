package net.brightroom.migration.generator.domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.alternativeParsing
import net.brightroom._extensions.kotlinx.datetime.now

/**
 * バージョン
 */
class Version constructor(
    private val value: LocalDateTime,
) {
    constructor() : this(LocalDateTime.now())

    fun asText(): String {
        val format =
            LocalDateTime.Format {
                year()
                monthNumber()
                day()
                hour()
                minute()
                second()
                alternativeParsing({}) {
                    secondFraction(3)
                }
            }
        return "V${value.format(format)}"
    }

    operator fun invoke(): LocalDateTime = value

    override fun toString(): String = value.toString()
}
