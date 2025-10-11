package net.brightroom.todo.presentation.endpoint.task.content

import am.ik.yavi.builder.validator
import am.ik.yavi.core.ConstraintViolations
import am.ik.yavi.core.Validator
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import net.brightroom.todo.domain.model.content.Content
import net.brightroom.todo.domain.model.identity.Id
import net.brightroom.todo.presentation.endpoint.Validatable

@Serializable
data class TaskContentModifyRequest(
    val id: Id,
    val content: Content,
) : Validatable<TaskContentModifyRequest> {
    @Transient
    override val validator: Validator<TaskContentModifyRequest> =
        validator {
            TaskContentModifyRequest::content nest Content.validator
        }

    override fun validate(): ConstraintViolations = validator.validate(this)
}
