package com.android.tododico

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.tododico.data.Todo
import com.android.tododico.presentation.AddEditTodoScreen
import com.android.tododico.presentation.MyNewScreen
import com.android.tododico.presentation.TodoListScreen
import com.android.tododico.presentation.component.TodoItem
import com.android.tododico.presentation.util.Routes
import com.android.tododico.ui.theme.TododicoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TododicoTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.TODO_LIST
                ) {
                    composable(Routes.TODO_LIST) {
                        TodoListScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                    composable(
                        route = Routes.ADD_EDIT_TODO + "?todoId={todoId}",
                        arguments = listOf(
                            navArgument(name = "todoId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        AddEditTodoScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                    }
                    composable(Routes.New_Screen + "?todoId={todoId}",
                        arguments = listOf(
                            navArgument(name = "todoId") {
                                type = NavType.IntType
                                defaultValue = 11
                            }
                        )) {
                        MyNewScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                    }
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TododicoTheme {

    }
}