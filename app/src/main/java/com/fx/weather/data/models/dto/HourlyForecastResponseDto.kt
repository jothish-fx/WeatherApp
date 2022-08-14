package com.fx.weather.data.models.dto

import com.fx.weather.core.utils.DateUtil
import com.fx.weather.core.utils.extensions.roundOffDecimal
import com.fx.weather.data.models.ui.City
import com.fx.weather.data.models.ui.Forecast
import com.fx.weather.data.models.ui.HourlyForecast
import com.fx.weather.data.models.ui.Weather
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class HourlyForecastResponseDto(

    @Json(name = "city")
    val cityDto: CityDto? = null,

    @Json(name = "cnt")
    val cnt: Int? = null,

    @Json(name = "cod")
    val cod: String? = null,

    @Json(name = "message")
    val message: Int? = null,

    @Json(name = "list")
    val list: List<ForecastDto>? = null
) {
    fun toHourlyForecast() = HourlyForecast(
        city = cityDto!!.toCity(),
        forecasts = list!!.map(ForecastDto::toForecast).groupBy { it.dayMonth }
    )
}

@JsonClass(generateAdapter = true)
data class Wind(

    @Json(name = "deg")
    val deg: Int? = null,

    @Json(name = "speed")
    val speed: Double? = null,

    @Json(name = "gust")
    val gust: Double? = null
)

@JsonClass(generateAdapter = true)
data class Clouds(

    @Json(name = "all")
    val all: Int? = null
)

@JsonClass(generateAdapter = true)
data class Sys(

    @Json(name = "pod")
    val pod: String? = null
)

@JsonClass(generateAdapter = true)
data class Coord(

    @Json(name = "lon")
    val lon: Int? = null,

    @Json(name = "lat")
    val lat: Int? = null
)

@JsonClass(generateAdapter = true)
data class ForecastDto(

    @Json(name = "dt")
    val dt: Int? = null,

    @Json(name = "pop")
    val pop: Double? = null,

    @Json(name = "visibility")
    val visibility: Int? = null,

    @Json(name = "dt_txt")
    val dtTxt: String? = null,

    @Json(name = "weather")
    val weather: List<WeatherItemDto>? = null,

    @Json(name = "main")
    val main: Main? = null,

    @Json(name = "clouds")
    val clouds: Clouds? = null,

    @Json(name = "sys")
    val sys: Sys? = null,

    @Json(name = "wind")
    val wind: Wind? = null
) {

    fun toForecast(): Forecast {
        val date = Date(dt!! * 1000L)
        return Forecast(
            dayMonth = DateUtil.getFormattedDate(date, "EEEE, dd MMMM"),
            hour = DateUtil.getFormattedDate(date, "HH:mm"),
            temp = main!!.temp!!.roundOffDecimal(),
            feelsLike = main.feelsLike!!.roundOffDecimal(),
            weather = weather!!.first().toWeather()
        )
    }
}


@JsonClass(generateAdapter = true)
data class Main(

    @Json(name = "temp")
    val temp: Double? = null,

    @Json(name = "temp_min")
    val tempMin: Double? = null,

    @Json(name = "grnd_level")
    val grndLevel: Int? = null,

    @Json(name = "temp_kf")
    val tempKf: Double? = null,

    @Json(name = "humidity")
    val humidity: Int? = null,

    @Json(name = "pressure")
    val pressure: Int? = null,

    @Json(name = "sea_level")
    val seaLevel: Int? = null,

    @Json(name = "feels_like")
    val feelsLike: Double? = null,

    @Json(name = "temp_max")
    val tempMax: Double? = null
)

@JsonClass(generateAdapter = true)
data class WeatherItemDto(

    @Json(name = "icon")
    val icon: String? = null,

    @Json(name = "description")
    val description: String? = null,

    @Json(name = "main")
    val main: String? = null,

    @Json(name = "id")
    val id: Int? = null
) {
    fun toWeather() = Weather(
        icon = icon!!,
        description = description!!,
        weatherName = main!!
    )
}


@JsonClass(generateAdapter = true)
data class CityDto(

    @Json(name = "country")
    val country: String? = null,

    @Json(name = "coord")
    val coord: Coord? = null,

    @Json(name = "sunrise")
    val sunrise: Int? = null,

    @Json(name = "timezone")
    val timezone: Int? = null,

    @Json(name = "sunset")
    val sunset: Int? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "population")
    val population: Int? = null
) {
    fun toCity() = City(
        name = name!!,
        country = country!!,
        sunrise = sunrise!!,
        sunset = sunset!!,
        timezone = timezone!!
    )
}