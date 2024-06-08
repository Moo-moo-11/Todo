package moomoo.todo.domain.comments.dto

data class CreateCommentRequest(
    val userId: Long,
    val comment: String
)
