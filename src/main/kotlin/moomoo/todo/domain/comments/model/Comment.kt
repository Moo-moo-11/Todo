package moomoo.todo.domain.comments.model

import jakarta.persistence.*
import moomoo.todo.domain.comments.dto.CommentResponse
import moomoo.todo.domain.todos.model.Todo
import moomoo.todo.domain.users.model.User
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "comment")
class Comment(
    @Column(name = "comment", nullable = false)
    var comment: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    var todo: Todo

) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @CreatedDate
    @Column(updatable = false, nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    fun updateComment(comment: String) {
        this.comment = comment
    }
}

fun Comment.toResponse() : CommentResponse {
    return CommentResponse(
        id = id!!,
        name = user.name,
        comment = comment,
        createdAt = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Seoul")))
    )
}