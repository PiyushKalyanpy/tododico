package com.android.tododico.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.tododico.ViewModels.AddEditTodoViewModel
import com.android.tododico.presentation.events.AddEditTodoEvent
import com.android.tododico.presentation.events.UiEvent

@Composable
fun AddEditTodoScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditTodoViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize(),
//        floatingActionButton = {
//            FloatingActionButton(
//                backgroundColor = Color.Black, onClick = {
//                    viewModel.onEvent(AddEditTodoEvent.onSaveTodoClick)
//
//                }) {
//                Icon(
//                    imageVector = Icons.Default.Check,
//                    contentDescription = "Save",
//                    tint = Color.White
//                )
//            }
//        }
    ) {

        Column(
            Modifier
                .padding(16.dp)

        ) {
            TextField(
                value = viewModel.title,
                onValueChange = {
                    viewModel.onEvent(AddEditTodoEvent.onTitleChange(it))
                },
                placeholder = {
                    Text(text = "Title")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.description,
                onValueChange = {
                    viewModel.onEvent(AddEditTodoEvent.onDescriptionChange(it))
                },
                placeholder = {
                    Text(text = "Description")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 5
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                onClick = { viewModel.onEvent(AddEditTodoEvent.onSaveTodoClick) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)

            ) {
                Text(text = "Add todo", color = Color.White)
            }
        }

    }

}