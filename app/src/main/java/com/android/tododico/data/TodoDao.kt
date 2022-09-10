package com.android.tododico.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("Select * from Todo where id = :id")
    suspend fun getTodoById(id: Int): Todo

    @Query("Select * from todo where isDone=0")
    fun getAllTodo(): Flow<List<Todo>>

    @Query("Select * from todo where isDone=1")
    fun getCompletedTodo(): Flow<List<Todo>>

}