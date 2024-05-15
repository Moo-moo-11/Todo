package moomoo.todo.domain.todos.controller

import moomoo.todo.domain.todos.dto.CreateTodoRequest
import moomoo.todo.domain.todos.dto.TodoResponse
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
    fun getTodoList(): ResponseEntity<List<TodoResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.getAllTodos())
    }

    @GetMapping("/{todoId}")
    fun getTodoById(@PathVariable todoId: Long): ResponseEntity<TodoResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.getTodoById(todoId))
    }

    @PostMapping()
    fun createTodo(@RequestBody request: CreateTodoRequest): ResponseEntity<TodoResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(todoService.createTodo(request))
    }

    @PutMapping("/{todoId}")
    fun updateTodo(@PathVariable todoId: Long, @RequestBody request: UpdateTodoRequest): ResponseEntity<TodoResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.updateTodo(todoId, request))
    }

    @DeleteMapping("/{todoId}")
    fun deleteTodo(@PathVariable todoId: Long): ResponseEntity<Unit> {
        todoService.deleteTodoById(todoId)
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .build()
    }
}