package com.example.weather.data.remote.dto

/**
 * Represents the DTO for the weather data.
 */
data class WeatherDataDto(
    val description: String = "",
    val icon: String = "",
    val id: Int = 0,
    val main: String = ""
)