package com.fx.weather.ui.hourlyforecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fx.weather.core.DataState
import com.fx.weather.data.models.ui.HourlyForecast
import com.fx.weather.data.repository.WeatherRepo
import com.fx.weather.ui.hourlyforecast.HourlyForecastUiState.HasItems
import com.fx.weather.ui.hourlyforecast.HourlyForecastUiState.NoItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HourlyForecastViewModel @Inject constructor(
    repo: WeatherRepo,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(HourlyForecastViewModelState())

    // UI state exposed to the UI
    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )


    init {
        repo.getHourlyForecast().onEach { dataState ->
            viewModelState.update {
                when (dataState) {
                    is DataState.Success -> {
                        it.copy(
                            isLoading = false,
                            hourlyForecast = dataState.data
                        )
                    }
                    DataState.Loading -> it.copy(isLoading = true)
                    is DataState.Error -> it.copy(
                        isLoading = false,
                        messages = it.messages + dataState.errorMessage
                    )
                    DataState.None -> it
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Notify that an error was displayed on the screen
     */
    fun messageShown(message: String) {
        viewModelState.update { currentUiState ->
            val messages = currentUiState.messages.filterNot { it == message }
            currentUiState.copy(messages = messages)
        }
    }

}

/**
 * UI state for the HourlyForecast.
 *
 * This is derived from [HourlyForecastViewModelState], but split into two possible subclasses to more
 * precisely represent the state available to render the UI.
 */
sealed interface HourlyForecastUiState {
    val isLoading: Boolean
    val messages: List<String>

    data class NoItems(
        override val isLoading: Boolean,
        override val messages: List<String>,
    ) : HourlyForecastUiState

    data class HasItems(
        val hourlyForecast: HourlyForecast,
        override val isLoading: Boolean,
        override val messages: List<String>,
    ) : HourlyForecastUiState
}

/**
 * An internal representation of the [HourlyForecastUiState], in a raw form
 */
data class HourlyForecastViewModelState(
    val isLoading: Boolean = false,
    val messages: List<String> = listOf(),
    val hourlyForecast: HourlyForecast? = null,
) {

    /**
     * Converts this [HourlyForecastViewModelState] into a more strongly typed [HourlyForecastUiState] for driving
     * the ui.
     */

    fun toUiState(): HourlyForecastUiState =
        if (hourlyForecast == null) {
            NoItems(
                isLoading = isLoading,
                messages = messages,
            )
        } else {
            HasItems(
                isLoading = isLoading,
                messages = messages,
                hourlyForecast = hourlyForecast
            )
        }
}