package moomoo.todo.domain.todos.dto

data class UpdateTodoRequest(
    val title: String,
    val name: String,
    val description: String?
)
