package com.android.tododico.data.Repository

import com.android.tododico.data.Todo
import com.android.tododico.data.TodoDao
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(
    private val dao: TodoDao
) : TodoRepository {
    override suspend fun insertTodo(todo: Todo) {
      return   dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
      return   dao.deleteTodo(todo)
    }

    override suspend fun getTodoById(id: Int): Todo {
      return   dao.getTodoById(id)
    }

    override fun getCompletedTodo(): Flow<List<Todo>> {
        return dao.getCompletedTodo()
    }

    override fun getAllTodo(): Flow<List<Todo>> {
       return  dao.getAllTodo()
    }
}