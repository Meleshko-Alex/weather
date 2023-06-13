package com.example.weather.data.remote.dto

import com.squareup.moshi.Json

data class WeatherDto(
    @Json(name = "lat") val latitude: Double = 0.0,
    @Json(name = "lon") val longitude: Double = 0.0,
    val timezone: String = "",
    @Json(name = "timezone_offset") val timezoneOffset: Int = 0,
    val hourly: List<OneHourWeatherDto> = listOf(),
    val daily: List<OneDayWeatherDto> = listOf()
)