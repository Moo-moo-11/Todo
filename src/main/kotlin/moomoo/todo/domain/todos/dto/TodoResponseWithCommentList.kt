package moomoo.todo.domain.todos.dto

import moomoo.todo.domain.comments.dto.CommentResponse

data class TodoResponseWithCommentList(
    val id: Long,
    val title: String,
    val name: String,
    val description: String?,
    val isCompleted: Boolean,
    val createdAt: String,
    val commentList: List<CommentResponse>
)
