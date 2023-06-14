package com.example.weather.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather.common.Constants

/**
 * Represents a daily weather entity in the local database.
 */
@Entity(tableName = Constants.DAILY_WEATHER_CACHE_TABLE_NAME)
data class OneDayWeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "weather_id") val weatherId: Int,
    @ColumnInfo(name = "time_date") val timeDate: Long,
    @ColumnInfo(name = "min_temp") val minTemp: Int,
    @ColumnInfo(name = "max_temp") val maxTemp: Int,
    val summary: String,
)