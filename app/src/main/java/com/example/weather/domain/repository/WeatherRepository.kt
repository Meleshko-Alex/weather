package com.example.weather.domain.repository

import com.example.weather.data.network.NetworkResult
import com.example.weather.domain.models.weather.CurrentAndHourlyWeather

interface WeatherRepository {

//    suspend fun getCurrentWeather(
//        latitude: Double,
//        longitude: Double,
//        units: String = Units.METRIC.name
//    ): NetworkResult<CurrentWeather>
//
//    suspend fun getWeatherForecast(
//        latitude: Double,
//        longitude: Double,
//        units: String = Units.METRIC.name,
//        timestampsCount: Int = DEFAULT_TIMESTAMPS
//    ): NetworkResult<WeatherForecast>

    suspend fun getCurrentAndHourlyWeather(
        latitude: Double,
        longitude: Double,
        units: String = Units.METRIC.name
    ): NetworkResult<CurrentAndHourlyWeather>

    companion object {

        // default number of timestamps is (24hrs / 3hr-step)
        const val DEFAULT_TIMESTAMPS = 8

        enum class Units(name: String) {
            KELVIN("standard"),
            METRIC("metric"),
            IMPERIAL("imperial")
        }
    }
}