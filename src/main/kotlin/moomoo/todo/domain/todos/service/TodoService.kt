package moomoo.todo.domain.todos.service

import moomoo.todo.domain.comments.dto.*
import moomoo.todo.domain.todos.dto.CreateTodoRequest
import moomoo.todo.domain.todos.dto.TodoResponse
import moomoo.todo.domain.todos.dto.TodoResponseWithCommentList
import moomoo.todo.domain.todos.dto.UpdateTodoRequest

interface TodoService {

    fun getAllTodoList() : List<TodoResponse>

    fun getTodoById(todoId: Long) : TodoResponseWithCommentList

    fun createTodo(request: CreateTodoRequest) : TodoResponse

    fun updateTodo(todoId: Long, request: UpdateTodoRequest) : TodoResponse

    fun deleteTodo(todoId: Long)

    fun toggleTodo(todoId: Long) : TodoResponse

    fun getCommentList(todoId: Long): List<CommentResponse>

    fun getComment(todoId: Long, commentId: Long) : CommentResponse

    fun addComment(todoId: Long, request: CreateCommentRequest) : CommentResponse

    fun updateComment(todoId: Long, commentId: Long, request: UpdateCommentRequest) : CommentResponse

    fun deleteComment(todoId: Long, commentId: Long, request: DeleteCommentRequest)

}