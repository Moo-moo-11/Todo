package moomoo.todo.domain.comments.dto

data class CommentResponse(
    val id: Long,
    val writer: String,
    val comment: String,
    val createdDateTime: String
)
