package com.example.weather.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather.common.Constants

/**
 * Represents a hourly weather entity in the local database
 */
@Entity(tableName = Constants.HOURLY_WEATHER_CACHE_TABLE_NAME)
data class OneHourWeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "time_date")
    val timeDate: Long,
    @ColumnInfo(name = "weather_id") val weatherId: Int,
    val temp: Int,
    @ColumnInfo(name = "feels_like") val feelsLikeTemperature: Int,
    val humidity: Int,
    val uvi: Int,
    @ColumnInfo("wind_speed") val windSpeed: Double
)

