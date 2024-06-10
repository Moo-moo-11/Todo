package moomoo.todo.domain.comments.dto

data class CommentResponse(
    val id: Long,
    val name: String,
    val comment: String,
    val createdAt: String
)
