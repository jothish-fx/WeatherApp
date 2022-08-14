package com.fx.weather.core


const val DEFAULT_ERROR_MESSAGE = "An unknown Error Occurred"

sealed class DataState<out T> {
    data class Success<out T>(val data: T) : DataState<T>()

    sealed interface Error {
        val exception: Throwable?
        val errorMessage: String
    }

    data class GenericError(
        override val exception: Throwable? = null,
        override val errorMessage: String = exception?.message ?: DEFAULT_ERROR_MESSAGE,
    ) : DataState<Nothing>(), Error

    data class NetworkError(
        override val exception: Throwable? = null,
        override val errorMessage: String = exception?.message ?: DEFAULT_ERROR_MESSAGE,
        val errorCode: Int? = null
    ) : DataState<Nothing>(), Error

    data class CustomError<E>(
        val errorResponse: E,
        val errorCode: Int,
        override val exception: Throwable? = null,
        override val errorMessage: String = exception?.message ?: DEFAULT_ERROR_MESSAGE,
    ) : DataState<Nothing>(), Error

    object Loading : DataState<Nothing>()
    object None : DataState<Nothing>()
}