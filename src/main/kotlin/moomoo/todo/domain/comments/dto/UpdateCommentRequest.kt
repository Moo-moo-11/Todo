package moomoo.todo.domain.comments.dto

data class UpdateCommentRequest(
    val name: String,
    val comment: String,
    val password: String
)
