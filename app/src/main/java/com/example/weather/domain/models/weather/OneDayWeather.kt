package com.example.weather.domain.models.weather

data class OneDayWeather(
    val weatherId: Int,
    val timeDate: Long,
    val minTemp: Int,
    val maxTemp: Int,
    val weather: WeatherType,
    val summary: String
)