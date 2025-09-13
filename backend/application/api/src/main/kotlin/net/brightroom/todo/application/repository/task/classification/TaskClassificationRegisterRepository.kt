package net.brightroom.todo.application.repository.task.classification

import net.brightroom.todo.domain.model.classification.Classification
import net.brightroom.todo.domain.model.identity.Id

interface TaskClassificationRegisterRepository {
    suspend fun register(
        classification: Classification,
        id: Id,
    )
}
