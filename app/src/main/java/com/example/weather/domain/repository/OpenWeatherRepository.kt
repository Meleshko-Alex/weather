package com.example.weather.domain.repository

import com.example.weather.common.Constants
import com.example.weather.data.remote.NetworkResult
import com.example.weather.domain.models.weather.HistoricalWeather
import com.example.weather.domain.models.weather.Weather

interface OpenWeatherRepository {

    suspend fun getWeather(
        latitude: Double,
        longitude: Double,
        units: String
    ): NetworkResult<Weather>

    suspend fun getHistoricalWeather(
        latitude: Double,
        longitude: Double,
        units: String,
        dateTime: Long
    ): NetworkResult<HistoricalWeather>
}