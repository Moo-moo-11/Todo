package moomoo.todo.domain.todos.dto

import moomoo.todo.domain.comments.dto.CommentResponse
import moomoo.todo.domain.comments.model.Comment
import moomoo.todo.domain.comments.model.toResponse
import moomoo.todo.domain.todos.model.Todo
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class TodoWithCommentsResponse(
    val id: Long,
    val title: String,
    val writer: String,
    val description: String,
    val isCompleted: Boolean,
    val createdDateTime: String,
    val lastUpdatedDateTime: String,
    val comments: List<CommentResponse>
)

fun Todo.toResponseWithComment(comments: List<Comment>): TodoWithCommentsResponse {
    return TodoWithCommentsResponse(
        id = id!!,
        title = title,
        writer = writer,
        description = description,
        isCompleted = isCompleted,
        createdDateTime = createdAt!!.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Seoul"))),
        lastUpdatedDateTime = updatedAt!!.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Seoul"))),
        comments = comments.map { it.toResponse() }
    )
}