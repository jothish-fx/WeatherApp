package com.fx.weather.data.repository

import com.fx.weather.core.DataState
import com.fx.weather.data.models.dto.HourlyForecastResponseDto
import com.fx.weather.data.network.SafeNetworkRequest
import com.fx.weather.data.network.api.WeatherService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class WeatherRepoImpl @Inject constructor(
    private val weatherService: WeatherService
) : WeatherRepo, SafeNetworkRequest() {

    override fun getHourlyForecast() = flow {
        emit(DataState.Loading)
        emit(
            apiRequest(
                dataRequest = { weatherService.getHourlyForecast() },
                map = HourlyForecastResponseDto::toHourlyForecast
            )
        )
    }
}