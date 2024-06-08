package moomoo.todo.domain.todos.dto

data class CreateTodoRequest(
    val userId: Long,
    val title: String,
    val description: String?
)
