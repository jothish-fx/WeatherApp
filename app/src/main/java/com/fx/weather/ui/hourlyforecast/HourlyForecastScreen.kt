package com.fx.weather.ui.hourlyforecast

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HourlyForecastScreen() {

    val viewModel: HourlyForecastViewModel = viewModel()

    val snackbarHostState = remember { SnackbarHostState() }

    val uiState = viewModel.uiState.collectAsState().value

    when {
        uiState.messages.isNotEmpty() -> {
            val message = remember(uiState) { uiState.messages[0] }
            LaunchedEffect(snackbarHostState) {
                snackbarHostState.showSnackbar(
                    message,
                    actionLabel = "Dismiss",
                    duration = SnackbarDuration.Long
                )
                viewModel.messageShown(message)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        HourlyForecastView(
            uiState = uiState
        )
    }

}