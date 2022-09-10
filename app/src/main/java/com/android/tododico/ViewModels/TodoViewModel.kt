package com.android.tododico.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.tododico.presentation.events.TodoListEvent
import com.android.tododico.data.Repository.TodoRepository
import com.android.tododico.data.Todo
import com.android.tododico.presentation.util.Routes
import com.android.tododico.presentation.events.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    val repository: TodoRepository
) : ViewModel() {

    val todos = repository.getAllTodo()
    val completedTodos = repository.getCompletedTodo()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private var deletedTodo : Todo? = null

    fun onEvent(event: TodoListEvent){
        when(event){
            is TodoListEvent.onTodoClick -> {
                sendUiEvents(UiEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${event.todo.id}"))
//                sendUiEvents(UiEvent.Navigate(Routes.New_Screen + "?todoId=${event.todo.id}"))
            }
            is TodoListEvent.onAddTodoClick -> {
               sendUiEvents(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is TodoListEvent.onUndoDeleteClick -> {
                deletedTodo?.let {
                    todo ->
                    viewModelScope.launch {
                        repository.insertTodo(todo)
                    }
                }
            }
            is TodoListEvent.onDeleteTodo -> {
               viewModelScope.launch {
                   deletedTodo=event.todo
                   repository.deleteTodo(event.todo)
                   sendUiEvents(
                       UiEvent.ShowSnackbar(
                       "Todo Deleted",
                       "Undo"
                   ))
               }
            }
            is TodoListEvent.onDoneChange -> {
                viewModelScope.launch {
                    repository.insertTodo(
                        event.todo.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }
        }
    }

    private fun sendUiEvents(uiEvent: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }

}