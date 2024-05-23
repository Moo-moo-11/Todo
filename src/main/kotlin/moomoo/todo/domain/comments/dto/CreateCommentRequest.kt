package moomoo.todo.domain.comments.dto

data class CreateCommentRequest(
    val name: String,
    val comment: String,
    val password: String
)
