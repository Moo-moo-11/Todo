package moomoo.todo.domain.todos.service

import moomoo.todo.domain.comments.dto.*
import moomoo.todo.domain.comments.model.Comment
import moomoo.todo.domain.comments.model.toResponse
import moomoo.todo.domain.comments.repository.CommentRepository
import moomoo.todo.domain.exception.ModelNotFoundException
import moomoo.todo.domain.exception.PasswordNotMatchedException
import moomoo.todo.domain.exception.TodoNotFoundException
import moomoo.todo.domain.todos.dto.*
import moomoo.todo.domain.todos.model.Todo
import moomoo.todo.domain.todos.model.toResponse
import moomoo.todo.domain.todos.repository.TodoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoServiceImpl(
    private val todoRepository: TodoRepository,
    private val commentRepository: CommentRepository
): TodoService {

    override fun getAllTodoList(sort: String, writer: String?): List<TodoResponse> {

        return when {
            (sort == "asc" && writer != null) -> if(!todoRepository.existsTodoByWriter(writer)){
                throw TodoNotFoundException(writer)
            } else {
                todoRepository.findAllByWriterOrderByCreatedAt(writer).map { it.toResponse() }
            }
            (sort == "asc") -> todoRepository.findAllByOrderByCreatedAt().map { it.toResponse() }
            (sort == "desc" && writer != null) ->  if(!todoRepository.existsTodoByWriter(writer)){
                throw TodoNotFoundException(writer)
            } else {
                todoRepository.findAllByWriterOrderByCreatedAtDesc(writer).map { it.toResponse() }
            }
            else -> todoRepository.findAllByOrderByCreatedAtDesc().map { it.toResponse() }
        }

    }

    override fun getTodoById(todoId: Long): TodoWithCommentsResponse {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId)
        val comments = commentRepository.findAllByTodoIdOrderByCreatedAt(todoId)

        return todo.toResponseWithComment(comments)
    }

    override fun createTodo(request: CreateTodoRequest): TodoResponse {
        val todo = Todo(
            title = request.title,
            writer = request.writer,
            description = request.description
        )

        return todoRepository.save(todo).toResponse()
    }

    @Transactional
    override fun updateTodo(todoId: Long, request: UpdateTodoRequest): TodoResponse {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId)

        todo.updateTodo(request)

        todoRepository.flush()

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
        if(!todoRepository.existsTodoById(todoId)) throw ModelNotFoundException("Todo", todoId)

        return commentRepository.findAllByTodoIdOrderByCreatedAt(todoId).map { it.toResponse() }
    }

    override fun getComment(todoId: Long, commentId: Long): CommentResponse {
        if(!todoRepository.existsTodoById(todoId)) throw ModelNotFoundException("Todo", todoId)

        val comment = commentRepository.findByTodoIdAndId(todoId, commentId) ?: throw ModelNotFoundException("Comment", commentId)

        return comment.toResponse()
    }

    @Transactional
    override fun addComment(todoId: Long, request: CreateCommentRequest): CommentResponse {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId)

        return Comment(
            writer = request.writer,
            comment = request.comment,
            password = request.password,
            todo = todo
        ).let { commentRepository.save(it) }.toResponse()
    }

    @Transactional
    override fun updateComment(todoId: Long, commentId: Long, request: UpdateCommentRequest): CommentResponse {
        if(!todoRepository.existsTodoById(todoId)) throw ModelNotFoundException("Todo", todoId)

        val comment = commentRepository.findByTodoIdAndId(todoId, commentId) ?: throw ModelNotFoundException("Comment", commentId)

        if (!comment.isValidPassword(request.writer, request.password)) throw PasswordNotMatchedException()

        comment.updateComment(request)

        return comment.toResponse()
    }

    @Transactional
    override fun deleteComment(todoId: Long, commentId: Long, request: DeleteCommentRequest) {
        if(!todoRepository.existsTodoById(todoId)) throw ModelNotFoundException("Todo", todoId)

        val comment = commentRepository.findByTodoIdAndId(todoId, commentId) ?: throw ModelNotFoundException("Comment", commentId)

        if (!comment.isValidPassword(request.writer, request.password)) throw PasswordNotMatchedException()

        commentRepository.delete(comment)
    }
}