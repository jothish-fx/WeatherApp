package com.fx.weather.data.models.ui

data class HourlyForecast(
    val city: City,
    val forecasts: Map<String, List<Forecast>>
)