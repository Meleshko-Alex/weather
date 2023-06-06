package com.example.weather.domain.models.weather

data class HourlyWeather(
    val current: OneHourWeather,
    val hourly: List<OneHourWeather>
)