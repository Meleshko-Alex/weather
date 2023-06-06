package com.example.weather.data.local.dao

import androidx.room.Dao
import androidx.room.DeleteTable
import androidx.room.Insert
import androidx.room.Query
import com.example.weather.common.Constants
import com.example.weather.data.local.entities.OneDayWeatherEntity
import com.example.weather.data.local.entities.OneHourWeatherEntity
import com.example.weather.domain.models.weather.HourlyWeather
import com.example.weather.domain.models.weather.OneHourWeather

@Dao
interface DailyWeatherDao {

    @Insert
    suspend fun insertDailyWeather(dailyWeather: List<OneDayWeatherEntity>)

    @Query("SELECT * FROM ${Constants.DAILYWEATHERCACHE_TABLENAME}")
    suspend fun getAllDailyWeather(): List<OneDayWeatherEntity>

    @Query("DELETE FROM ${Constants.DAILYWEATHERCACHE_TABLENAME}")
    suspend fun deleteAllDailyWeather()
}