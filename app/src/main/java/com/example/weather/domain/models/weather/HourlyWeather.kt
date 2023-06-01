package com.example.weather.domain.models.weather

data class HourlyWeather(
    val current: CurrentWeather,
    val hourly: List<CurrentWeather>
) {
    data class CurrentWeather(
        val timeDate: Long,
        val temp: Int,
        val feelsLikeTemperature: Int,
        val humidity: Int,
        val uvi: Int,
        val windSpeed: Double,
        val weather: WeatherType
    )
}