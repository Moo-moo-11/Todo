package moomoo.todo.domain.todos.repository

import moomoo.todo.domain.todos.model.Todo
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository: JpaRepository<Todo, Long> {

    fun findAllByOrderByCreatedAtDesc(): MutableList<Todo>

    fun findAllByOrderByCreatedAt(): MutableList<Todo>

    fun findAllByWriterOrderByCreatedAt(writer: String): MutableList<Todo>

    fun findAllByWriterOrderByCreatedAtDesc(writer: String): MutableList<Todo>

    fun existsTodoById(id: Long): Boolean

    fun existsTodoByWriter(writer: String): Boolean

}