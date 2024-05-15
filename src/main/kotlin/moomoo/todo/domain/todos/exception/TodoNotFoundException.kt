package moomoo.todo.domain.todos.exception

class TodoNotFoundException(
    private val todoId: Long
): RuntimeException("Todo not found with given id: $todoId")