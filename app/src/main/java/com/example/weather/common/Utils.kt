package com.example.weather.common

import com.example.weather.domain.models.weather.WeatherType
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object Utils {
    fun getWeatherIcon(
        weather: WeatherType,
        time: Int = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH")).toInt()
    ): Int {
        return if (time >= Constants.NIGHT_TIME || time < Constants.DAY_TIME) {
            weather.icon.iconNight
        } else {
            weather.icon.iconDay
        }
    }

    fun convertEpochToLocalDate(epochTime: Long): String {
        val instant =
            Instant.ofEpochMilli(epochTime * 1000) // Multiply by 1000 to convert to milliseconds
        val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("EEEE, d MMM")
        return localDateTime.format(formatter)
    }
}