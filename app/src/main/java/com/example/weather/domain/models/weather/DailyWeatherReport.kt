package com.example.weather.domain.models.weather

data class DailyWeatherReport(
    val summary: String,
    val minTemp: Int,
    val maxTemp: Int,
    val windSpeed: Double,
    val precipitationProbability: Int,
    val pressure: Int
)