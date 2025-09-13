package net.brightroom.todo.presentation.endpoint.task

import am.ik.yavi.builder.validator
import am.ik.yavi.core.ConstraintViolations
import am.ik.yavi.core.Validator
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import net.brightroom.todo.domain.model.classification.Classification
import net.brightroom.todo.domain.model.content.Content
import net.brightroom.todo.domain.model.lifecycle.Lifecycle
import net.brightroom.todo.domain.model.planning.Planning
import net.brightroom.todo.presentation.endpoint.Validatable

@Serializable
data class TaskRegisterRequest(
    val content: Content,
    val planning: Planning = Planning(),
    val lifecycle: Lifecycle = Lifecycle(),
    val classification: Classification = Classification(),
) : Validatable<TaskRegisterRequest> {
    @Transient
    override val validator: Validator<TaskRegisterRequest> =
        validator {
            TaskRegisterRequest::content nest Content.validator
        }

    override fun validate(): ConstraintViolations = validator.validate(this)
}
