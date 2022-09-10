package com.android.tododico.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.tododico.R
import com.android.tododico.data.Todo
import com.android.tododico.presentation.events.TodoListEvent


@Composable
fun TodoItem(
    todo: Todo,
    onEvent: (TodoListEvent) -> Unit,
    showDescription: Boolean,
    modifier: Modifier = Modifier
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(all = 16.dp)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(weight = 1f)
                .padding(end = 24.dp)

        ) {
            MyCheckBox(
                todo = todo,
                checked = todo.isDone,
                onCheckedChange = { isChecked ->
                    onEvent(TodoListEvent.onDoneChange(todo, isChecked))
                }
            )
            Spacer(
                modifier = Modifier
                    .width(width = 22.dp)
            )
            Column {
                Text(
                    text = todo.title,
                    style = if (todo.isDone == true) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle(
                        textDecoration = TextDecoration.None
                    ),
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontFamily(
                        Font(R.font.inter_medium),
                    )
                )
                Spacer(
                    modifier = Modifier
                        .height(height = 4.dp)
                )
                if (showDescription){
                    CustomText(
                        textContent = todo.description,
                        maxLines = 1,
                        fontFamily = FontFamily(Font(R.font.inter_regular)),
                        fontSize = 14.sp
                    )
                }

            }
        }
        IconButton(onClick = {
            onEvent(TodoListEvent.onDeleteTodo(todo))
        }) {
            Icon(
                painter = painterResource(id = R.drawable.delete),
                contentDescription = "Delete",
                tint = Color(0xffBFCAD3)
            )
        }
    }
}

@Composable
fun CustomText(
    textContent: String,
    isDone: Boolean = false,
    maxLines: Int,
    fontFamily: FontFamily,
    fontSize: TextUnit,
    ) {
    Text(
        text = textContent,
        fontSize = fontSize,
        style = if (isDone == true) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle(
            textDecoration = TextDecoration.None
        ),
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        fontFamily = fontFamily

    )
}
