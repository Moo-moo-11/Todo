package moomoo.todo.domain.comments.dto

data class UpdateCommentRequest(
    val writer: String,
    val comment: String,
    val password: String
)
