package moomoo.todo.domain.todos.model

import jakarta.persistence.*
import moomoo.todo.domain.comments.model.Comment
import moomoo.todo.domain.comments.model.toResponse
import moomoo.todo.domain.todos.dto.TodoResponse
import moomoo.todo.domain.todos.dto.TodoResponseWithCommentList
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@EntityListeners(AuditingEntityListener::class)
@Entity (name = "todo")
class Todo(

    @Column
    var title: String,

    @Column
    var name: String,

    @Column
    var description: String?,

    @Column (name = "is_completed")
    var isCompleted: Boolean = false

) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @CreatedDate
    @Column(updatable = false, nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    fun updateTodo(title: String, name: String, description: String?) {
        this.title = title
        this.name = name
        this.description = description
    }

}

fun Todo.toResponse(): TodoResponse {
    return TodoResponse(
        id = id!!,
        title = title,
        name = name,
        description = description,
        isCompleted = isCompleted,
        createdAt = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Seoul")))
    )
}

fun Todo.toResponseWithCommentList(commentList: List<Comment>): TodoResponseWithCommentList {
    return TodoResponseWithCommentList(
        id = id!!,
        title = title,
        name = name,
        description = description,
        isCompleted = isCompleted,
        createdAt = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Seoul"))),
        commentList = commentList.map { it.toResponse() }
    )
}