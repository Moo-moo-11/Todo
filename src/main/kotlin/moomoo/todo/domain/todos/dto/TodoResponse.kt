package moomoo.todo.domain.todos.dto

data class TodoResponse(
    val id: Long,
    val title: String,
    val name: String,
    val description: String?,
    val isCompleted: Boolean,
    val createdAt: String
)
