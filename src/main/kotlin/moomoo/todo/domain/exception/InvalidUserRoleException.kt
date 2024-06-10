package moomoo.todo.domain.exception

class InvalidUserRoleException(
    private val role: String
) : RuntimeException("Invalid user role: $role")