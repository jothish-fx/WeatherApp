/**
 * Created by
 * @author Jothish on 09/03/2022, 15:44
 * Last modified 09/03/2022, 15:44
 */

package com.fx.weather.data.network

import com.fx.weather.core.network.NetworkHelper
import com.fx.weather.core.network.NoConnectivityException
import com.fx.weather.core.network.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NoConnectionInterceptor @Inject constructor(
    private val networkHelper: NetworkHelper,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!networkHelper.isConnectionOn()) {
            throw NoConnectivityException("Please check your internet connection")
        } else if (!networkHelper.isInternetAvailable()) {
            throw NoInternetException("Unable to connect to the Internet")
        } else {
            chain.proceed(chain.request())
        }
    }
}