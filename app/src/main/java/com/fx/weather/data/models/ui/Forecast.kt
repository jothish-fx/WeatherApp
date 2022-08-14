package com.fx.weather.data.models.ui

data class Forecast(
    val dayMonth: String,
    val hour: String,
    val temp: Double,
    val feelsLike: Double,
    val weather: Weather
)
