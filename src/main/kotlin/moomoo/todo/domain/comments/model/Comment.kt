package moomoo.todo.domain.comments.model

import jakarta.persistence.*
import moomoo.todo.domain.comments.dto.CommentResponse
import moomoo.todo.domain.todos.model.Todo
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "comment")
class Comment(

    @Column
    var name: String,

    @Column
    var comment: String,

    @Column
    val password: String,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "todo_id")
    var todo: Todo

) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @CreatedDate
    @Column(updatable = false, nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    fun isValidPassword(requestPassword: String) : Boolean{
        return password == requestPassword
    }
}

fun Comment.toResponse() : CommentResponse {
    return CommentResponse(
        id = id!!,
        name = name,
        comment = comment,
        createdAt = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Seoul")))
    )
}