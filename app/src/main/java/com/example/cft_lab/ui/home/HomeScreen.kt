package com.example.cft_lab.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cft_lab.ui.theme.Cft_labTheme

@Composable
fun HomeScreen(
    uiState: HomeViewModel.UiState, onIntent: (HomeIntent) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { onIntent(HomeIntent.OpenHelloDialog) },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text("Приветствие")
        }
        if (uiState.dialogIsOpen)
            AlertDialog(
                title = {
                    Text(
                        text = "Hello \uD83D\uDE03", color = MaterialTheme.colorScheme.onBackground
                    )
                },
                text = {
                    if (uiState.isLoading) CircularProgressIndicator()
                    else
                        Text(
                            text = uiState.userName,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                },
                onDismissRequest = { onIntent(HomeIntent.CloseHelloDialog) },
                confirmButton = {
                    Button(onClick = { onIntent(HomeIntent.CloseHelloDialog) }) {
                        Text("Ok!")
                    }
                }
            )
    }

}


@Composable
@Preview
fun HomePreview() {
    Cft_labTheme {
        HomeScreen(uiState = HomeViewModel.UiState(), onIntent = {})
    }
}