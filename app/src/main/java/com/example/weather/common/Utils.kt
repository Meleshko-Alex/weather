package com.example.weather.common

import com.example.weather.domain.models.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Utils {
    fun getWeatherIcon(
        weather: WeatherType,
        time: Int = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH")).toInt()
    ): Int {
        return if (time >= Constants.NIGHT_TIME || time < Constants.DAY_TIME) {
            weather.icon.iconNormalNight
        } else {
            weather.icon.iconNormalDay
        }
    }
}