package moomoo.todo.domain.todos.repository

import moomoo.todo.domain.todos.model.Todo
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository: JpaRepository<Todo, Long> {
    fun findAllByOrderByCreatedAtDesc(): List<Todo>
}