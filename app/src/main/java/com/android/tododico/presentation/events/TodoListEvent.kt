package com.android.tododico.presentation.events

import com.android.tododico.data.Todo

sealed class TodoListEvent {
    data class onDeleteTodo(val todo : Todo) : TodoListEvent()
    data class onDoneChange(val todo: Todo, val isDone : Boolean) : TodoListEvent()
    object onUndoDeleteClick : TodoListEvent()
    data class onTodoClick(val todo : Todo) : TodoListEvent()
    object onAddTodoClick : TodoListEvent()


}