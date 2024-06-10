package moomoo.todo.domain.exception

class UserIdentifierAlreadyExistsException(
    private val userIdentifier: String
) : RuntimeException("UserIdentifier $userIdentifier already exists")
