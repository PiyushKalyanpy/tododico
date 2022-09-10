package com.android.tododico.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.tododico.ViewModels.TodoViewModel
import com.android.tododico.data.Todo

@Composable
fun MyCheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    viewModel: TodoViewModel = hiltViewModel(),
    todo: Todo

) {

    val greenColor = Color(0xff199E2E)

    Row {
        Card(
            modifier = Modifier.background(Color.White),
            elevation = 0.dp,
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(1.5.dp, color = greenColor)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(if (todo.isDone) greenColor else Color.White)
                    .clickable {
                        todo.isDone = !todo.isDone
                        onCheckedChange(todo.isDone)
                    },
                contentAlignment = Alignment.Center
            ) {
                if (todo.isDone)
                    Icon(Icons.Default.Check, contentDescription = "", tint = Color.White)
            }
        }


    }
}