package com.android.tododico.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.tododico.presentation.events.AddEditTodoEvent
import com.android.tododico.data.Repository.TodoRepository
import com.android.tododico.data.Todo
import com.android.tododico.presentation.events.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
    private val repository: TodoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var todo by mutableStateOf<Todo?>(null)
        private set
    var title by mutableStateOf("Sample todo title")
        private set
    var description by mutableStateOf("this is sample todo section and i will be writing the sample code here")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val todoId = savedStateHandle.get<Int>("todoId")!!
        if (todoId != -1) {
            viewModelScope.launch {
                repository.getTodoById(todoId).let { todo ->
                    title = todo.title
                    description = todo.description
                    this@AddEditTodoViewModel.todo = todo
                }

            }
        }

    }

    fun onEvent(event: AddEditTodoEvent) {
        when (event) {
            is AddEditTodoEvent.onTitleChange -> {
                title = event.title
            }
            is AddEditTodoEvent.onDescriptionChange -> {
                description = event.description
            }
            is AddEditTodoEvent.onSaveTodoClick -> {
                viewModelScope.launch {
                    if (title.isBlank() || description.isBlank()) {
                        sendUiEvent(
                            UiEvent.ShowSnackbar(
                                "Title or Description Can't be empty",

                                )
                        )
                        return@launch
                    }
                    repository.insertTodo(
                        Todo(
                            title = title,
                            description = description,
                            isDone = todo?.isDone ?: false,
                            id = todo?.id
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }

    }


}