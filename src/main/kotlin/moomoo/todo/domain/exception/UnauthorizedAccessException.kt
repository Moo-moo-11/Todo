package moomoo.todo.domain.exception

data class UnauthorizedAccessException(
    val type: String
) : RuntimeException(
    "You are not the owner of this $type"
)
