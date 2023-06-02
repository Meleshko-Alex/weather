package com.example.weather.domain.repository

import com.example.weather.data.remote.NetworkResult
import com.example.weather.domain.models.weather.DailyWeather
import com.example.weather.domain.models.weather.HourlyWeather

interface OpenWeatherRepository {

    suspend fun getHourlyWeather(
        latitude: Double,
        longitude: Double,
        units: String = Units.METRIC.value
    ): NetworkResult<HourlyWeather>

    suspend fun getDailyWeather(
        latitude: Double,
        longitude: Double,
        units: String = Units.METRIC.value
    ): NetworkResult<DailyWeather>

    companion object {

        enum class Units(val value: String) {
            KELVIN("standard"),
            METRIC("metric"),
            IMPERIAL("imperial")
        }
    }
}