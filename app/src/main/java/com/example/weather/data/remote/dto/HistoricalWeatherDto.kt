package com.example.weather.data.remote.dto

import com.squareup.moshi.Json

data class HistoricalWeatherDto(
    @Json(name = "lat") val latitude: Double = 0.0,
    @Json(name = "lon") val longitude: Double = 0.0,
    val timezone: String = "",
    @Json(name = "timezone_offset") val timezoneOffset: Int = 0,
    val data: List<OneHourWeatherDto> = listOf()
)