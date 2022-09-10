package com.android.tododico.ViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.tododico.data.Repository.TodoRepository
import com.android.tododico.presentation.events.NewScreenEvents
import com.android.tododico.presentation.events.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewScreenViewModel @Inject constructor(
    val repository: TodoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // titles as string

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var title by mutableStateOf("")


//    fun onEvent(event : NewScreenEvents){
//        when(event){
//            is NewScreenEvents.onPopBackStack -> {
//
//            }
//        }
//
//    }

    init {
        val todoId = savedStateHandle.get<Int>("todoId")!!
        Log.d("Main", "sdfsdf$todoId")
        viewModelScope.launch {
            repository.getTodoById(todoId).let {  todo ->
                title = todo.title
            }
        }
    }


}