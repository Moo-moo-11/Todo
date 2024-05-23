package moomoo.todo.domain.todos.controller

import jakarta.validation.Valid
import moomoo.todo.domain.todos.dto.CreateTodoRequest
import moomoo.todo.domain.todos.dto.TodoResponse
import moomoo.todo.domain.todos.dto.TodoWithCommentsResponse
import moomoo.todo.domain.todos.dto.UpdateTodoRequest
import moomoo.todo.domain.todos.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/todos")
@RestController
class TodoController(
    val todoService: TodoService
) {
    @GetMapping()
    fun getTodoList(@RequestParam(value = "sort", required = false) sort: String?,
                    @RequestParam(value = "writer", required = false) writer: String?): ResponseEntity<List<TodoResponse>> {
        val sorting = if(sort != null && sort != "asc") "desc" else "asc"
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.getAllTodoList(sorting, writer))
    }

    @GetMapping("/{todoId}")
    fun getTodoById(@PathVariable todoId: Long): ResponseEntity<TodoWithCommentsResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.getTodoById(todoId))
    }

    @PostMapping()
    fun createTodo(@Valid @RequestBody request: CreateTodoRequest): ResponseEntity<TodoResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(todoService.createTodo(request))
    }

    @PutMapping("/{todoId}")
    fun updateTodo(@PathVariable todoId: Long, @Valid @RequestBody request: UpdateTodoRequest): ResponseEntity<TodoResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.updateTodo(todoId, request))
    }

    @DeleteMapping("/{todoId}")
    fun deleteTodo(@PathVariable todoId: Long): ResponseEntity<Unit> {
        todoService.deleteTodo(todoId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }

    @PatchMapping("/{todoId}")
    fun toggleTodo(@PathVariable todoId: Long): ResponseEntity<TodoResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.toggleTodo(todoId))
    }
}