package com.example.weather.domain.models

import java.time.LocalDateTime

data class CurrentWeather(
    val cityName: String = "",
    val weather: Weather = Weather(),
    val mainInfo: MainInfo = MainInfo(),
    val windSpeed: Double = 0.0,
    val time: LocalDateTime? = null
) {
    data class Weather(
        val main: String = "",
        val icon: String = ""
    )

    data class MainInfo(
        val feelsLike: Double = 0.0,
        val temperature: Double = 0.0,
        val tempMax: Double = 0.0,
        val tempMin: Double = 0.0
    )
}

