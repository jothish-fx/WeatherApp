package com.fx.weather.data.network

import com.fx.weather.core.DataState
import com.fx.weather.core.network.NoConnectivityException
import com.fx.weather.core.network.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException

abstract class SafeNetworkRequest {

    suspend fun <T : Any> apiRequest(
        dataRequest: suspend () -> T,
    ): DataState<T> {
        return try {
            DataState.Success(
                withContext(Dispatchers.IO) {
                    dataRequest.invoke()
                }
            )
        } catch (throwable: Throwable) {
            Timber.e(throwable, "API Error")
            when (throwable) {
                is IOException -> DataState.GenericError(
                    throwable,
                    throwable.message ?: ""
                )
                is HttpException -> {
                    DataState.NetworkError(
                        throwable,
                        getErrorMessage(throwable.code())
                    )

                }
                is SocketTimeoutException -> DataState.NetworkError(
                    throwable, "Timed out"
                )
                is NoConnectivityException,
                is NoInternetException -> DataState.NetworkError(
                    throwable,
                    throwable.message!!
                )

                else -> {
                    DataState.GenericError(throwable, throwable.message ?: "")
                }
            }
        }
    }

    suspend fun <T : Any, M : Any> apiRequest(
        dataRequest: suspend () -> T, map: ((T) -> M)
    ): DataState<M> {
        return try {
            val data = withContext(Dispatchers.IO) {
                dataRequest.invoke()
            }
            DataState.Success(map(data))
        } catch (throwable: Throwable) {
            Timber.e(throwable, "API Error")
            when (throwable) {
                is IOException -> DataState.GenericError(
                    throwable,
                    throwable.message ?: ""
                )
                is HttpException -> {
                    DataState.NetworkError(
                        throwable,
                        getErrorMessage(throwable.code())
                    )

                }
                is SocketTimeoutException -> DataState.NetworkError(
                    throwable, "Timed out"
                )
                is NoConnectivityException,
                is NoInternetException -> DataState.NetworkError(
                    throwable,
                    throwable.message!!
                )

                else -> {
                    DataState.GenericError(throwable, throwable.message ?: "")
                }
            }
        }
    }


    private fun getErrorMessage(code: Int): String {
        return when (code) {
            -1 -> "Timeout"
            401 -> "Unauthorised"
            400 -> "Bad Request"
            404 -> "Not found"
            500 -> "Internal Server Error"
            else -> "Something went wrong"
        }
    }
}

