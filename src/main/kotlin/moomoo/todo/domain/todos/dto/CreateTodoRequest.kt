package moomoo.todo.domain.todos.dto

data class CreateTodoRequest(
    val title: String,
    val description: String?
)
