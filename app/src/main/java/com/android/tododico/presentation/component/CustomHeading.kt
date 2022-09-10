package com.android.tododico.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.tododico.R

@Composable
fun CustomHeadings(
    heading: String,
    listSize: Int? = null
) {
    Text(
        text = buildAnnotatedString {
            append(heading)
            if (listSize!=null || listSize==0){
                withStyle(
                    style = SpanStyle(
                        color = Color(0xff7b7b7b),
                    )
                ) {
                    append(" (${listSize.toString()})")
                }
            }
        },
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        modifier = Modifier.padding(16.dp),
        fontSize = 24.sp
    )

}
