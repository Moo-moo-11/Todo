package moomoo.todo.domain.todos.dto

data class CreateTodoRequest(
    val title: String,
    val name: String,
    val description: String?
)
