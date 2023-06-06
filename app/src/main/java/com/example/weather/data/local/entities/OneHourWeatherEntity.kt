package com.example.weather.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather.domain.models.weather.WeatherType

@Entity(tableName = "hourly_weather_cache")
data class OneHourWeatherEntity(
    @PrimaryKey
    @ColumnInfo(name = "weather_id") val weatherId: Int,
    @ColumnInfo(name = "time_date") val timeDate: Long,
    val temp: Int,
    @ColumnInfo(name = "feels_like") val feelsLikeTemperature: Int,
    val humidity: Int,
    val uvi: Int,
    @ColumnInfo("wind_speed") val windSpeed: Double,
    val weather: String
)

