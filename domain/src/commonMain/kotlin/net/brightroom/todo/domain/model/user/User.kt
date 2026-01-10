package net.brightroom.todo.domain.model.user

import net.brightroom.todo.domain.model.user.identity.Identity
import net.brightroom.todo.domain.model.user.profile.Profile

/**
 * ユーザー
 */
data class User(private val identity: Identity, private val profile: Profile)
