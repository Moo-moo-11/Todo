package moomoo.todo.domain.exception

class TodoNotFoundException(
    private val writer: String
): RuntimeException("Todo not found with given name: $writer")
