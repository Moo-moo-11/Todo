package moomoo.todo.domain.todos.service

import moomoo.todo.domain.todos.dto.CreateTodoRequest
import moomoo.todo.domain.todos.dto.TodoResponse
import moomoo.todo.domain.todos.dto.UpdateTodoRequest

interface TodoService {

    fun getAllTodos() : List<TodoResponse>

    fun getTodoById(todoId: Long) : TodoResponse

    fun createTodo(request: CreateTodoRequest) : TodoResponse

    fun updateTodo(todoId: Long, request: UpdateTodoRequest) : TodoResponse

    fun deleteTodoById(todoId: Long)

}