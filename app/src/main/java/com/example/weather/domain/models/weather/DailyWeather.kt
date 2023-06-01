package com.example.weather.domain.models.weather

data class DailyWeather(
    val daily: List<OneDayWeather>
) {
    data class OneDayWeather(
        val timeDate: Long,
        val minTemp: Int,
        val maxTemp: Int,
        val weather: WeatherType
    )
}