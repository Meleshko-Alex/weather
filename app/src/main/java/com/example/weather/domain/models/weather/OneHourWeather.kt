package com.example.weather.domain.models.weather

data class OneHourWeather(
    val weatherId: Int,
    val timeDate: Long,
    val temp: Int,
    val feelsLikeTemperature: Int,
    val humidity: Int,
    val uvi: Int,
    val windSpeed: Double,
    val weather: WeatherType
)

