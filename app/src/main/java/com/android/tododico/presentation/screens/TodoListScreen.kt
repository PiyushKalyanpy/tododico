package com.android.tododico.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.tododico.R
import com.android.tododico.ViewModels.TodoViewModel
import com.android.tododico.presentation.component.CustomHeadings
import com.android.tododico.presentation.component.TodoItem
import com.android.tododico.presentation.events.TodoListEvent
import com.android.tododico.presentation.events.UiEvent

@Composable
fun TodoListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TodoViewModel = hiltViewModel()
) {
    val todos = viewModel.todos.collectAsState(initial = emptyList())
    val completedTodo = viewModel.completedTodos.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(TodoListEvent.onUndoDeleteClick)
                    }
                }
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
//        modifier = Modifier.verticalScroll(state = rememberScrollState()),
        floatingActionButton = {
            FloatingActionButton(

                backgroundColor = Color.Black,
                onClick = {
                    viewModel.onEvent(TodoListEvent.onAddTodoClick)
                }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        }
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color(0xffEBEBEB)
                )

        ) {

            Spacer(modifier = Modifier.height(40.dp))
            CustomHeadings(heading = "Your Todos", listSize = todos.value.size)
            if (todos.value.size == 0 && completedTodo.value.size == 0) {
                Image(
                    painter = painterResource(id = R.drawable.group_1),
                    contentDescription = "unknowen",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(52.dp)
                )
            }
            val interactionSource = MutableInteractionSource()

            val coroutineScope = rememberCoroutineScope()
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)

            ) {
                items(todos.value) { todo ->
                    TodoItem(

                        todo = todo,
                        onEvent = viewModel::onEvent,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.onEvent(TodoListEvent.onTodoClick(todo))
                            }, showDescription = false
                    )
                }
            }

            CustomHeadings(heading = "Completed Todo's", completedTodo.value.size)

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clipToBounds()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 23.dp)
            ) {
                items(completedTodo.value) { todo ->
                    TodoItem(
                        todo = todo,
                        onEvent = viewModel::onEvent,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.onEvent(TodoListEvent.onTodoClick(todo))
                            }, showDescription = false

                    )
                }
            }


        }

    }
}
