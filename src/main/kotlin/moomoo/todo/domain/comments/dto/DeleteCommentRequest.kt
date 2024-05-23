package moomoo.todo.domain.comments.dto

data class DeleteCommentRequest(
    val writer: String,
    val password: String
)
