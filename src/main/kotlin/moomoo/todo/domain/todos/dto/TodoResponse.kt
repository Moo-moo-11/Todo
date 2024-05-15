package moomoo.todo.domain.todos.dto

import java.util.*

data class TodoResponse(
    val id: Long,
    val title: String,
    val name: String,
    val description: String?,
    val date: Date
)
