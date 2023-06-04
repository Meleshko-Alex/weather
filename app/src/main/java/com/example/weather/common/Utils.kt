package com.example.weather.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.fragment.app.Fragment
import com.example.weather.domain.models.cities.TopCities
import com.example.weather.domain.models.weather.WeatherType
import com.google.gson.Gson
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.prefs.Preferences

object Utils {
    /**
     * Returns the resource id of the [weather] icon depending on the [forecast time][time]
     */
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

    /**
     * Returns the local date in the specified [format] from the given [epoch][epochTime]
     */
    fun convertEpochToLocalDate(
        epochTime: Long,
        format: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, d MMM")
    ): String {
        val instant = Instant.ofEpochMilli(epochTime * 1000) // Multiply by 1000 to convert to milliseconds
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).format(format)
    }
}