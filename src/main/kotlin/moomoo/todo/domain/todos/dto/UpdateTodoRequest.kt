package moomoo.todo.domain.todos.dto

data class UpdateTodoRequest(
    val title: String,
    val description: String?
)
