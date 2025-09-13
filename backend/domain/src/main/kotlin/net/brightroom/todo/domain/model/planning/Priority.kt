package net.brightroom.todo.domain.model.planning

import kotlinx.serialization.Serializable

/**
 * 優先度
 */
@Serializable
enum class Priority {
    Critical,
    High,
    Medium,
    Low,
}
