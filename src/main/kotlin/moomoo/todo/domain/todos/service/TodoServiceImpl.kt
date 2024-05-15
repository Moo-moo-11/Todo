package moomoo.todo.domain.todos.service

import moomoo.todo.domain.todos.dto.CreateTodoRequest
import moomoo.todo.domain.todos.dto.TodoResponse
import moomoo.todo.domain.todos.dto.UpdateTodoRequest
import moomoo.todo.domain.todos.exception.TodoNotFoundException
import moomoo.todo.domain.todos.model.Todo
import moomoo.todo.domain.todos.model.toResponse
import moomoo.todo.domain.todos.repository.TodoRepository
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoServiceImpl(
    private val todoRepository: TodoRepository,
): TodoService {

    override fun getAllTodos(): List<TodoResponse> {
        return todoRepository.findAll(Sort.by(Sort.Direction.DESC,"date")
            .and(Sort.by(Sort.Direction.DESC,"id"))).map { it.toResponse() }
    }

    override fun getTodoById(todoId: Long): TodoResponse {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw TodoNotFoundException(todoId)
        return todo.toResponse()
    }

    @Transactional
    override fun createTodo(request: CreateTodoRequest): TodoResponse {
        val todo = Todo(
            title = request.title,
            name = request.name,
            description = request.description,
            date = request.date
        )
        return todoRepository.save(todo).toResponse()
    }

    @Transactional
    override fun updateTodo(todoId: Long, request: UpdateTodoRequest): TodoResponse {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw TodoNotFoundException(todoId)
        todo.title = request.title
        todo.name = request.name
        todo.description = request.description
        return todoRepository.save(todo).toResponse()
    }

    @Transactional
    override fun deleteTodoById(todoId: Long) {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw TodoNotFoundException(todoId)
        todoRepository.delete(todo)
    }
}