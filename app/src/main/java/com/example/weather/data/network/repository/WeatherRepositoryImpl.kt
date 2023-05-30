package com.example.weather.data.network.repository

import com.example.weather.data.network.NetworkResult
import com.example.weather.data.network.WeatherService
import com.example.weather.data.network.mappers.toCurrentWeather
import com.example.weather.domain.models.CurrentWeather
import com.example.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val weatherService: WeatherService) :
    WeatherRepository {

    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
    ): NetworkResult<CurrentWeather> {
        return try {
            val response = weatherService.getCurrentWeather(latitude, longitude)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!.toCurrentWeather())
            } else {
                throw RuntimeException("Response wasn't successful: ${response.message()}")
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            NetworkResult.Error(exception.message ?: "An unknown error occurred")
        }
    }
}