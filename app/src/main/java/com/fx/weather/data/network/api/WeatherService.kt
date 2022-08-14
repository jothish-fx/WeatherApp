/**
 * Created by
 * @author Jothish on 24/02/2022, 14:53
 * Last modified 24/02/2022, 14:53
 */

package com.fx.weather.data.network.api

import com.fx.weather.BuildConfig
import com.fx.weather.data.models.dto.HourlyForecastResponseDto
import com.fx.weather.data.network.NetworkingConstants.Forecast
import com.fx.weather.data.network.NetworkingConstants.Key
import com.fx.weather.data.network.NetworkingConstants.Sample
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET(Forecast.HOURLY_FORECAST)
    suspend fun getHourlyForecast(
        @Query(Key.LAT) lat: Int = Sample.LAT,
        @Query(Key.LON) long: Int = Sample.LON,
        @Query(Key.UNITS) units: String = Sample.UNIT,
        @Query(Key.APP_ID) appId: String = BuildConfig.API_KEY
    ): HourlyForecastResponseDto

}