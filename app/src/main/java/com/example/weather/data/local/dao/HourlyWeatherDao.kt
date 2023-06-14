package com.example.weather.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weather.common.Constants
import com.example.weather.data.local.entities.OneHourWeatherEntity

@Dao
interface HourlyWeatherDao {
    /**
     * Inserts a list of [OneHourWeatherEntity] objects into the database.
     * @param hourlyWeather The list of hourly weather entities to be inserted.
     */
    @Insert
    suspend fun insertHourlyWeather(hourlyWeather: List<OneHourWeatherEntity>)

    /**
     * Retrieves all hourly weather data from the database.
     * @return a list of [OneHourWeatherEntity] objects representing all the hourly weather data in the database.
     */
    @Query("SELECT * FROM ${Constants.HOURLY_WEATHER_CACHE_TABLE_NAME}")
    suspend fun getAllHourlyWeather(): List<OneHourWeatherEntity>

    /**
     * Deletes all hourly weather data from the database.
     */
    @Query("DELETE FROM ${Constants.HOURLY_WEATHER_CACHE_TABLE_NAME}")
    suspend fun deleteAllHourlyWeather()
}