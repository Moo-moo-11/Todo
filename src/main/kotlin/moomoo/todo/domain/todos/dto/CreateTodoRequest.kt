package moomoo.todo.domain.todos.dto

import java.util.Date

data class CreateTodoRequest(
    val title: String,
    val name: String,
    val description: String?,
    val date: Date
)
