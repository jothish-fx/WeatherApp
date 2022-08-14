/**
 * Created by
 * @author Jothish on 09/03/2022, 16:21
 * Last modified 09/03/2022, 16:17
 */

package com.fx.weather.core.network

import java.io.IOException

class ApiException(message: String) : IOException(message)

class NoInternetException(message: String = "Unable to connect to the Internet") :
    IOException(message)

class NoConnectivityException(message: String = "Please check your internet connection") :
    IOException(message)