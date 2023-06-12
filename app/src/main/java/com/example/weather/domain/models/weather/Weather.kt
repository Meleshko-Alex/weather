package com.example.weather.domain.models.weather

data class Weather(
    val current: OneHourWeather,
    val hourly: List<OneHourWeather>,
    val daily: List<OneDayWeather>
)