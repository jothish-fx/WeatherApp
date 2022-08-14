/**
 * Created by
 * @author Jothish on 09/03/2022, 16:18
 * Last modified 09/03/2022, 16:17
 */

package com.fx.weather.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHelper @Inject constructor(@ApplicationContext private val context: Context) {

    fun isConnectionOn(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities = connectivityManager.activeNetwork ?: return false

        val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        val result = when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
            else -> false
        }

        return result
    }

    fun isInternetAvailable(): Boolean {
        return try {
            val timeoutMs = 1500
            val sock = Socket()
            val sockaddr = InetSocketAddress("8.8.8.8", 53)

            sock.connect(sockaddr, timeoutMs)
            sock.close()

            true
        } catch (e: IOException) {
            false
        }
    }

    fun assertHasInternet() {
        if (!isConnectionOn()) {
            throw NoConnectivityException()
        } else if (!isInternetAvailable()) {
            throw NoInternetException()
        }
    }


/*
    inline fun <R> runIfHasInternet(block: () -> R): R {
        return try {
            //Result.success(block())
            if (!isConnectionOn()) {
                throw NoConnectivityException("Please check your internet connection")
            } else if (!isInternetAvailable()) {
                throw NoInternetException("Unable to connect to the Internet")
            } else block()
        } catch (e: Throwable) {
            throw  e
        }
    }*/


}