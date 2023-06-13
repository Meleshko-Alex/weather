package com.example.weather.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weather.common.Constants
import com.example.weather.data.local.entities.OneHourWeatherEntity

@Dao
interface HourlyWeatherDao {

    @Insert
    suspend fun insertHourlyWeather(hourlyWeather: List<OneHourWeatherEntity>)

    @Query("SELECT * FROM ${Constants.HOURLY_WEATHER_CACHE_TABLENAME}")
    suspend fun getAllHourlyWeather(): List<OneHourWeatherEntity>

    @Query("DELETE FROM ${Constants.HOURLY_WEATHER_CACHE_TABLENAME}")
    suspend fun deleteAllHourlyWeather()
}