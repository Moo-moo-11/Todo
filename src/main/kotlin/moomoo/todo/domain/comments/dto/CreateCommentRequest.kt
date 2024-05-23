package moomoo.todo.domain.comments.dto

data class CreateCommentRequest(
    val writer: String,
    val comment: String,
    val password: String
)
