package moomoo.todo.domain.comments.controller

import moomoo.todo.domain.comments.dto.CommentResponse
import moomoo.todo.domain.comments.dto.CreateCommentRequest
import moomoo.todo.domain.comments.dto.DeleteCommentRequest
import moomoo.todo.domain.comments.dto.UpdateCommentRequest
import moomoo.todo.domain.todos.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/todos/{todoId}/comments")
@RestController
class CommentController(
    val todoService: TodoService
) {

    @GetMapping()
    fun getCommentList(@PathVariable todoId: Long): ResponseEntity<List<CommentResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.getCommentList(todoId))
    }

    @GetMapping("/{commentId}")
    fun getComment(@PathVariable todoId: Long, @PathVariable commentId: Long): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.getComment(todoId,commentId))
    }

    @PostMapping()
    fun createComment(@PathVariable todoId: Long, @RequestBody request: CreateCommentRequest): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(todoService.addComment(todoId, request))
    }

    @PutMapping("/{commentId}")
    fun updateComment(@PathVariable todoId: Long, @PathVariable commentId: Long,
                      @RequestBody request: UpdateCommentRequest): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.updateComment(todoId, commentId, request))

    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(@PathVariable todoId: Long, @PathVariable commentId: Long,
                      @RequestBody request: DeleteCommentRequest): ResponseEntity<Unit> {
        todoService.deleteComment(todoId, commentId, request)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}