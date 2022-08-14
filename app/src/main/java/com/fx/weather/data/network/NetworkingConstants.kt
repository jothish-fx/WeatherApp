/**
 * Created by
 * @author Jothish on 28/02/2022, 12:42
 * Last modified 28/02/2022, 12:42
 */

package com.fx.weather.data.network


object NetworkingConstants {

    object Forecast {
        private const val FORECAST = "forecast"
        const val HOURLY_FORECAST = FORECAST
    }

    object Key {
        const val LAT = "lat"
        const val LON = "lon"
        const val APP_ID = "appid"
        const val UNITS = "units"
    }

    object Sample {
        const val LAT = 25
        const val LON = 55
        const val UNIT = "metric"
    }

}