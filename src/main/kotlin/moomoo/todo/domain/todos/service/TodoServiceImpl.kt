package moomoo.todo.domain.todos.service

import moomoo.todo.domain.comments.dto.*
import moomoo.todo.domain.comments.model.Comment
import moomoo.todo.domain.comments.model.toResponse
import moomoo.todo.domain.comments.repository.CommentRepository
import moomoo.todo.domain.exception.ModelNotFoundException
import moomoo.todo.domain.exception.PasswordNotMatchedException
import moomoo.todo.domain.todos.dto.CreateTodoRequest
import moomoo.todo.domain.todos.dto.TodoResponse
import moomoo.todo.domain.todos.dto.TodoResponseWithCommentList
import moomoo.todo.domain.todos.dto.UpdateTodoRequest
import moomoo.todo.domain.todos.model.Todo
import moomoo.todo.domain.todos.model.toResponse
import moomoo.todo.domain.todos.model.toResponseWithCommentList
import moomoo.todo.domain.todos.repository.TodoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoServiceImpl(
    private val todoRepository: TodoRepository,
    private val commentRepository: CommentRepository
): TodoService {

    override fun getAllTodoList(): List<TodoResponse> {
        return todoRepository.findAllByOrderByCreatedAtDesc().map { it.toResponse() }
    }

    override fun getTodoById(todoId: Long): TodoResponseWithCommentList {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId)

        val commentList = commentRepository.findAllByTodoIdOrderByCreatedAt(todoId)

        return todo.toResponseWithCommentList(commentList)
    }

    @Transactional
    override fun createTodo(request: CreateTodoRequest): TodoResponse {
        val todo = Todo(
            title = request.title,
            name = request.name,
            description = request.description
        )

        return todoRepository.save(todo).toResponse()
    }

    @Transactional
    override fun updateTodo(todoId: Long, request: UpdateTodoRequest): TodoResponse {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId)

        todo.updateTodo(request.title, request.name, request.description)

        return todo.toResponse()
    }

    @Transactional
    override fun deleteTodo(todoId: Long) {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId)

        commentRepository.deleteAllByTodoId(todoId)

        todoRepository.delete(todo)
    }

    @Transactional
    override fun toggleTodo(todoId: Long): TodoResponse {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId)

        todo.toggleTodo()

        return todo.toResponse()
    }

    override fun getCommentList(todoId: Long): List<CommentResponse> {
        if(!todoRepository.existsById(todoId)) throw ModelNotFoundException("Todo", todoId)

        return commentRepository.findAllByTodoIdOrderByCreatedAt(todoId).map { it.toResponse() }
    }

    override fun getComment(todoId: Long, commentId: Long): CommentResponse {
        if(!todoRepository.existsById(todoId)) throw ModelNotFoundException("Todo", todoId)

        val comment = commentRepository.findByTodoIdAndId(todoId, commentId) ?: throw ModelNotFoundException("Comment", commentId)

        return comment.toResponse()
    }

    @Transactional
    override fun addComment(todoId: Long, request: CreateCommentRequest): CommentResponse {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId)

        val comment = Comment(
            name = request.name,
            comment = request.comment,
            password = request.password,
            todo = todo
        )

        return commentRepository.save(comment).toResponse()
    }

    @Transactional
    override fun updateComment(todoId: Long, commentId: Long, request: UpdateCommentRequest): CommentResponse {
        if(!todoRepository.existsById(todoId)) throw ModelNotFoundException("Todo", todoId)

        val comment = commentRepository.findByTodoIdAndId(todoId, commentId) ?: throw ModelNotFoundException("Comment", commentId)

        if (!comment.isValidPassword(request.password)) throw PasswordNotMatchedException()

        comment.updateComment(request.name, request.comment)

        return comment.toResponse()
    }

    @Transactional
    override fun deleteComment(todoId: Long, commentId: Long, request: DeleteCommentRequest) {
        if(!todoRepository.existsById(todoId)) throw ModelNotFoundException("Todo", todoId)

        val comment = commentRepository.findByTodoIdAndId(todoId, commentId) ?: throw ModelNotFoundException("Comment", commentId)

        if (!comment.isValidPassword(request.password)) throw PasswordNotMatchedException()

        commentRepository.delete(comment)
    }
}