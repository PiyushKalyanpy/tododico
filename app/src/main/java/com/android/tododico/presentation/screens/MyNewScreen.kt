package com.android.tododico.presentation

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.tododico.ViewModels.NewScreenViewModel
import com.android.tododico.presentation.events.UiEvent

@Composable
fun MyNewScreen(
    onPopBackStack : () -> Unit,
    viewModel : NewScreenViewModel  = hiltViewModel()

) {

    Button(onClick = {onPopBackStack() }) {
        Text(text = viewModel.title)
    }
}