package moomoo.todo.domain.exception

class ModelNotFoundException(
    private val model: String,
    private val id: Long
): RuntimeException("$model not found with given id: $id")