package com.example.weather.domain.repository

import com.example.weather.data.remote.NetworkResult
import com.example.weather.domain.models.weather.DailyWeather
import com.example.weather.domain.models.weather.DailyWeatherReport
import com.example.weather.domain.models.weather.HourlyWeather
import com.example.weather.domain.models.weather.Weather

interface OpenWeatherRepository {

    suspend fun getWeather(
        latitude: Double,
        longitude: Double,
        units: String = Units.METRIC.value
    ): NetworkResult<Weather>

    companion object {

        enum class Units(val value: String) {
            KELVIN("standard"),
            METRIC("metric"),
            IMPERIAL("imperial")
        }
    }
}