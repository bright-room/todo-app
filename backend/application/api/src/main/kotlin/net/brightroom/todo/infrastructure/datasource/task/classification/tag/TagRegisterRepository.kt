package net.brightroom.todo.infrastructure.datasource.task.classification.tag

import net.brightroom.todo.domain.model.classification.tag.Tag

interface TagRegisterRepository {
    suspend fun register(tag: Tag): TagId
}
