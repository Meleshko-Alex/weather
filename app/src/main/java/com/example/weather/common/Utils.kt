package com.example.weather.common

import com.example.weather.domain.models.weather.WeatherType
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

object Utils {
    private const val NIGHT_TIME = 21 // constant represents time - 21:00 - and is used for choosing a day/night icon
    private const val DAY_TIME = 6 // constant represents time - 06:00 - and is used for choosing a day/night icon

    /**
     * Returns the resource ID of the weather icon based on the given [weather] type and [time].
     *
     * @param weather the type of weather.
     * @param time the current hour in 24-hour format. Defaults to the current hour of the system.
     * @return the resource ID of the weather icon.
     */
    fun getWeatherIcon(
        weather: WeatherType,
        time: Int = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH")).toInt()
    ): Int {
        return if (time >= NIGHT_TIME || time < DAY_TIME) {
            weather.icon.iconNight
        } else {
            weather.icon.iconDay
        }
    }

    /**
    * Converts the given [epoch] timestamp to a localized date string using the specified [format].
    *
    * @param epoch The epoch timestamp to convert to a localized date string.
    * @param format The format of the output date string. Defaults to "EEEE, d MMM".
    * @return The localized date string.
    */
    fun convertEpochToLocalDate(
        epoch: Long,
        format: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, d MMM", Locale.US)
    ): String {
        val instant =
            Instant.ofEpochMilli(epoch * 1000) // Multiply by 1000 to convert to milliseconds
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).format(format)
    }
}