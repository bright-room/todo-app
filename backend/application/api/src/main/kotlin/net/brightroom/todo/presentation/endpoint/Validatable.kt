package net.brightroom.todo.presentation.endpoint

import am.ik.yavi.core.ConstraintViolations
import am.ik.yavi.core.Validator

interface Validatable<T> {
    val validator: Validator<T>

    fun validate(): ConstraintViolations
}
