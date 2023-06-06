package com.example.weather.data.local.dao

import androidx.room.Dao
import androidx.room.DeleteTable
import androidx.room.Insert
import androidx.room.Query
import com.example.weather.common.Constants
import com.example.weather.data.local.entities.OneHourWeatherEntity
import com.example.weather.domain.models.weather.HourlyWeather
import com.example.weather.domain.models.weather.OneHourWeather

@Dao
interface HourlyWeatherDao {

    @Insert
    suspend fun insertHourlyWeather(hourlyWeather: List<OneHourWeatherEntity>)

    @Query("SELECT * FROM ${Constants.HOURLYWEATHERCACHE_TABLENAME}")
    suspend fun getAllHourlyWeather(): List<OneHourWeatherEntity>

    @Query("DELETE FROM ${Constants.HOURLYWEATHERCACHE_TABLENAME}")
    suspend fun deleteAllHourlyWeather()
}