package net.brightroom.todo.domain.model.task.lifecycle.transition

import kotlinx.datetime.LocalDate
import net.brightroom._extensions.kotlinx.datetime.now
import net.brightroom.todo.domain.model.project.lifecycle.transition.BeganDate
import net.brightroom.todo.domain.model.project.lifecycle.transition.CompletedDate
import net.brightroom.todo.domain.model.project.lifecycle.transition.Transition
import kotlin.test.Test
import kotlin.test.assertEquals

class TransitionTest {
    @Test
    fun `タスクの遷移状況が開始前の時、遷移状況が存在しない状態で生成される`() {
        val transition = Transition.of()
        assertEquals(Transition.None, transition)
    }

    @Test
    fun `タスクの遷移状況が進行中の時、遷移状況が開始済みの状態で生成される`() {
        val date = LocalDate.now()

        val transition = Transition.of(BeganDate(date))
        assertEquals(Transition.Started(BeganDate(date)), transition)
    }

    @Test
    fun `タスクの遷移状況が完了済みの時、遷移状況が完了済みの状態で生成される`() {
        val date = LocalDate.now()

        val transition = Transition.of(BeganDate(date), CompletedDate(date))
        assertEquals(Transition.Completed(BeganDate(date), CompletedDate(date)), transition)
    }
}
