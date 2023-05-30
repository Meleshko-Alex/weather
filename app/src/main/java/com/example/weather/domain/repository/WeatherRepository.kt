package com.example.weather.domain.repository

import com.example.weather.data.network.NetworkResult
import com.example.weather.data.network.WeatherService
import com.example.weather.domain.models.CurrentWeather
import retrofit2.Response

interface WeatherRepository {

    suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
    ): NetworkResult<CurrentWeather>
}