package net.brightroom.todo.presentation.endpoint

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val type: String = "about:blank",
    val title: String,
    val status: Int,
    val detail: String,
    val instance: String,
)
