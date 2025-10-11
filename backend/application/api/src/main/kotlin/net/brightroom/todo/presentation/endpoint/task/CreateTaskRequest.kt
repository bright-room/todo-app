package net.brightroom.todo.presentation.endpoint.task

import am.ik.yavi.builder.validator
import am.ik.yavi.core.ConstraintViolations
import am.ik.yavi.core.Validator
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import net.brightroom.todo.domain.model.content.Description
import net.brightroom.todo.domain.model.content.Title
import net.brightroom.todo.presentation.endpoint.Validatable

@Serializable
data class CreateTaskRequest(
    val title: Title,
    val description: Description = Description(""),
) : Validatable<CreateTaskRequest> {
    @Transient
    override val validator: Validator<CreateTaskRequest> =
        validator {
            CreateTaskRequest::title nest Title.validator
        }

    override fun validate(): ConstraintViolations = validator.validate(this)
}
