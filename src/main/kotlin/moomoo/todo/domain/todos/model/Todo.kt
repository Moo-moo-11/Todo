package moomoo.todo.domain.todos.model

import jakarta.persistence.*
import moomoo.todo.domain.todos.dto.TodoResponse
import java.util.Date

@Entity (name = "todo")
class Todo(

    @Column
    var title: String,

    @Column
    var name: String,

    @Column
    var description: String?,

    @Temporal(TemporalType.DATE)
    var date: Date

) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Todo.toResponse(): TodoResponse {
    return TodoResponse(
        id = id!!,
        title = title,
        name = name,
        description = description,
        date = date
    )
}