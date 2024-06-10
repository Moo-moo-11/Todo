package moomoo.todo.domain.todos.model

import jakarta.persistence.*
import moomoo.todo.domain.comments.model.Comment
import moomoo.todo.domain.comments.model.toResponse
import moomoo.todo.domain.todos.dto.TodoResponse
import moomoo.todo.domain.todos.dto.TodoResponseWithCommentList
import moomoo.todo.domain.users.model.User
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@EntityListeners(AuditingEntityListener::class)
@Entity (name = "todo")
class Todo(

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "description", nullable = true)
    var description: String?,

    @Column (name = "is_completed", nullable = false)
    var isCompleted: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User

) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @CreatedDate
    @Column(updatable = false, nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    fun updateTodo(title: String, description: String?) {
        this.title = title
        this.description = description
    }

    fun toggleTodo() {
        isCompleted = !isCompleted
    }

}

fun Todo.toResponse(): TodoResponse {
    return TodoResponse(
        id = id!!,
        title = title,
        name = user.name,
        description = description,
        isCompleted = isCompleted,
        createdAt = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Seoul")))
    )
}

fun Todo.toResponseWithCommentList(commentList: List<Comment>): TodoResponseWithCommentList {
    return TodoResponseWithCommentList(
        id = id!!,
        title = title,
        name = user.name,
        description = description,
        isCompleted = isCompleted,
        createdAt = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Seoul"))),
        commentList = commentList.map { it.toResponse() }
    )
}