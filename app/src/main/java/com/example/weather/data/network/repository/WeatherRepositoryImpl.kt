package com.example.weather.data.network.repository

import com.example.weather.data.network.NetworkResult
import com.example.weather.data.network.WeatherService
import com.example.weather.data.network.mappers.toCurrentAndHourlyWeather
import com.example.weather.data.network.mappers.toDailyWeather
import com.example.weather.domain.models.weather.DailyWeather
import com.example.weather.domain.models.weather.HourlyWeather
import com.example.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val weatherService: WeatherService) :
    WeatherRepository {

    override suspend fun getHourlyWeather(
        latitude: Double,
        longitude: Double,
        units: String
    ): NetworkResult<HourlyWeather> {
        return try {
            val response = weatherService.getHourlyWeather(
                latitude = latitude,
                longitude = longitude,
                units = units
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