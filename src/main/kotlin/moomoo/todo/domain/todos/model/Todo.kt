package moomoo.todo.domain.todos.model

import jakarta.persistence.*
import moomoo.todo.domain.todos.dto.TodoResponse
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@EntityListeners(AuditingEntityListener::class)
@Entity (name = "todo2")
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
    var createdAt: LocalDateTime? = null

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null

}

fun Todo.toResponse(): TodoResponse {
    return TodoResponse(
        id = id!!,
        title = title,
        name = name,
        description = description,
        isCompleted = isCompleted,
        createdDateTime = createdAt!!.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Seoul"))),
        lastUpdatedDateTime = updatedAt!!.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Seoul")))
    )
}