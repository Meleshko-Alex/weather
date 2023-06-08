package com.example.weather.data.repository

import com.example.weather.data.remote.NetworkResult
import com.example.weather.data.remote.api.OpenWeatherService
import com.example.weather.data.remote.toCurrentAndHourlyWeather
import com.example.weather.data.remote.toDailyWeather
import com.example.weather.domain.models.weather.DailyWeather
import com.example.weather.domain.models.weather.HourlyWeather
import com.example.weather.domain.repository.OpenWeatherRepository
import javax.inject.Inject

class OpenWeatherRepositoryImpl @Inject constructor(private val weatherService: OpenWeatherService) :
    OpenWeatherRepository {

    override suspend fun getHourlyWeather(
        latitude: Double,
        longitude: Double,
        units: String
    ): NetworkResult<HourlyWeather> {
        return try {
            val response = weatherService.getHourlyWeather(
                latitude = latitude,
                longitude = longitude,
                units = units.lowercase()
            )
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!.toCurrentAndHourlyWeather())
            } else {
                throw RuntimeException("Request error: ${response.message()}")
            }
        } catch (exception: Exception) {
            NetworkResult.Error(exception.message!!)
        }
    }

    override suspend fun getDailyWeather(
        latitude: Double,
        longitude: Double,
        units: String
    ): NetworkResult<DailyWeather> {
        return try {
            val response = weatherService.getDailyWeather(
                latitude = latitude,
                longitude = longitude,
                units = units
            )
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!.toDailyWeather())
            } else {
                throw RuntimeException("Request error: ${response.message()}")
            }
        } catch (exception: Exception) {
            NetworkResult.Error(exception.message!!)
        }
    }
}