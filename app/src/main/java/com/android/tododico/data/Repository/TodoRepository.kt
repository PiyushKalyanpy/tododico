package com.android.tododico.data.Repository

import com.android.tododico.data.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    suspend fun getTodoById(id: Int): Todo

    fun getCompletedTodo() : Flow<List<Todo>>

    fun getAllTodo(): Flow<List<Todo>>
}