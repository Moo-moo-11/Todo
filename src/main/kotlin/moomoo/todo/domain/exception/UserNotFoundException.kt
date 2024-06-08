package moomoo.todo.domain.exception

class UserNotFoundException(
    private val userIdentifier: String
) : RuntimeException("User with identifier $userIdentifier not found")