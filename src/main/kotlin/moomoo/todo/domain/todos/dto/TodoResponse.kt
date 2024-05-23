package moomoo.todo.domain.todos.dto

data class TodoResponse(
    val id: Long,
    val title: String,
    val writer: String,
    val description: String,
    val isCompleted: Boolean,
    val createdDateTime: String,
    val lastUpdatedDateTime: String
)
