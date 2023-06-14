package com.example.weather.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weather.common.Constants
import com.example.weather.data.local.entities.OneDayWeatherEntity

@Dao
interface DailyWeatherDao {
    /**
     * Inserts a list of [OneDayWeatherEntity] objects into the database.
     * @param dailyWeather The list of daily weather entities to be inserted.
     */
    @Insert
    suspend fun insertDailyWeather(dailyWeather: List<OneDayWeatherEntity>)

    /**
     * Retrieves all daily weather data from the database.
     * @return A list of [OneDayWeatherEntity] objects representing all the daily weather data in the database.
     */
    @Query("SELECT * FROM ${Constants.DAILY_WEATHER_CACHE_TABLE_NAME}")
    suspend fun getAllDailyWeather(): List<OneDayWeatherEntity>

    /**
     * Deletes all daily weather data from the database.
     */
    @Query("DELETE FROM ${Constants.DAILY_WEATHER_CACHE_TABLE_NAME}")
    suspend fun deleteAllDailyWeather()
}