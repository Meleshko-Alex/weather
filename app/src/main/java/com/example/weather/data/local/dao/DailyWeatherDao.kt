package com.example.weather.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weather.common.Constants
import com.example.weather.data.local.entities.OneDayWeatherEntity

@Dao
interface DailyWeatherDao {

    @Insert
    suspend fun insertDailyWeather(dailyWeather: List<OneDayWeatherEntity>)

    @Query("SELECT * FROM ${Constants.DAILY_WEATHER_CACHE_TABLENAME}")
    suspend fun getAllDailyWeather(): List<OneDayWeatherEntity>

    @Query("DELETE FROM ${Constants.DAILY_WEATHER_CACHE_TABLENAME}")
    suspend fun deleteAllDailyWeather()
}