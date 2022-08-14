package com.fx.weather.data.repository

import com.fx.weather.core.DataState
import com.fx.weather.data.models.ui.HourlyForecast
import kotlinx.coroutines.flow.Flow

interface WeatherRepo {
    fun getHourlyForecast(): Flow<DataState<HourlyForecast>>
}