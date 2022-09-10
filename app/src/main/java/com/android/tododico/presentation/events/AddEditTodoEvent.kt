package com.android.tododico.presentation.events

sealed class AddEditTodoEvent{
    data class onTitleChange(val title : String ) : AddEditTodoEvent()
    data class onDescriptionChange(val description : String) : AddEditTodoEvent()
     object onSaveTodoClick : AddEditTodoEvent()
}
