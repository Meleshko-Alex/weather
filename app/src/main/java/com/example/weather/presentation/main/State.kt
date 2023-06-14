package com.example.weather.presentation

sealed class State<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : State<T>(data = data)
    class Error<T>(message: String) : State<T>(message = message)
    class Loading<T>() : State<T>()
}