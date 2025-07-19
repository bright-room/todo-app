package net.brightroom.todo.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
data class TaskRepeatRuleId(val value: String) {
    companion object {
        fun generate(): TaskRepeatRuleId = TaskRepeatRuleId(UUID.randomUUID().toString())
        fun from(value: String): TaskRepeatRuleId = TaskRepeatRuleId(value)
    }
}

@Serializable
enum class RepeatRuleType {
    NONE,           // 繰り返しなし
    DAILY,          // 毎日
    WEEKDAYS_ONLY,  // 平日のみ
    WEEKENDS_ONLY,  // 休日のみ
    WEEKLY,         // 週単位
    MONTHLY,        // 月単位
    YEARLY          // 年単位
}

@Serializable
enum class DayOfWeek {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

@OptIn(ExperimentalTime::class)
@Serializable
data class TaskRepeatRule(
    val id: TaskRepeatRuleId,
    val taskId: TaskId,
    val repeatType: RepeatRuleType,
    @Contextual val createdAt: Instant,
    val weeklyConfig: WeeklyRepeatConfig? = null,
    val monthlyConfig: MonthlyRepeatConfig? = null,
    val yearlyConfig: YearlyRepeatConfig? = null
) {
    init {
        when (repeatType) {
            RepeatRuleType.WEEKLY -> require(weeklyConfig != null) { "Weekly repeat rule must have weekly config" }
            RepeatRuleType.MONTHLY -> require(monthlyConfig != null) { "Monthly repeat rule must have monthly config" }
            RepeatRuleType.YEARLY -> require(yearlyConfig != null) { "Yearly repeat rule must have yearly config" }
            else -> {
                require(weeklyConfig == null && monthlyConfig == null && yearlyConfig == null) {
                    "Non-specific repeat rules should not have specific configs"
                }
            }
        }
    }
}

@Serializable
data class WeeklyRepeatConfig(
    val daysOfWeek: Set<DayOfWeek>,
    val intervalWeeks: Int = 1 // Up to 10 weeks as per requirements
) {
    init {
        require(daysOfWeek.isNotEmpty()) { "Weekly repeat must specify at least one day" }
        require(intervalWeeks in 1..10) { "Weekly interval must be between 1 and 10 weeks" }
    }
}

@Serializable
data class MonthlyRepeatConfig(
    val dayOfMonth: Int,
    val intervalMonths: Int = 1 // Up to 10 months as per requirements
) {
    init {
        require(dayOfMonth in 1..31) { "Day of month must be between 1 and 31" }
        require(intervalMonths in 1..10) { "Monthly interval must be between 1 and 10 months" }
    }
}

@Serializable
data class YearlyRepeatConfig(
    val monthOfYear: Int,
    val dayOfMonth: Int,
    val intervalYears: Int = 1 // Up to 10 years as per requirements
) {
    init {
        require(monthOfYear in 1..12) { "Month of year must be between 1 and 12" }
        require(dayOfMonth in 1..31) { "Day of month must be between 1 and 31" }
        require(intervalYears in 1..10) { "Yearly interval must be between 1 and 10 years" }
    }
}