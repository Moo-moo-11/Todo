package moomoo.todo.domain.todos.dto

import jakarta.validation.constraints.Size

data class UpdateTodoRequest(
    @field:Size(min = 1, max = 200, message = "제목은 1~200글자 이어야합니다")
    val title: String,
    val writer: String,
    @field:Size(min = 1, max = 1000, message = "본문은 1~1000글자 이어야합니다")
    val description: String
)
