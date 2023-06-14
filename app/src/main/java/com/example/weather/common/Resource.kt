package com.example.weather.common

/**
 * Represents the result of a network operation.
 *
 * @param T the type of data contained in the result.
 * @property data the data contained in the result, if the operation was successful. Default value is null.
 * @property message the error message, if the operation encountered an error. Default value is null.
 */
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    /**
     * Represents a successful network result with the specified data.
     * @param data the data contained in the result.
     */
    class Success<T>(data: T?) : Resource<T>(data)

    /**
     * Represents an error network result with the specified error message.
     * @param message the error message.
     */
    class Error<T>(message: String) : Resource<T>(message = message)
}