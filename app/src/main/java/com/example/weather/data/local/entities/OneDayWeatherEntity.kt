package com.example.weather.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather.common.Constants

@Entity(tableName = Constants.DAILYWEATHERCACHE_TABLENAME)
data class OneDayWeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "weather_id") val weatherId: Int,
    @ColumnInfo(name = "time_date") val timeDate: Long,
    @ColumnInfo(name = "min_temp") val minTemp: Int,
    @ColumnInfo(name = "max_temp") val maxTemp: Int,
)