package moomoo.todo.domain.comments.repository

import moomoo.todo.domain.comments.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {

    fun findAllByTodoId(todoId: Long): List<Comment>

    fun findByTodoIdAndId(todoId: Long, commentId: Long): Comment?

    fun deleteAllByTodoId(todoId: Long)
}