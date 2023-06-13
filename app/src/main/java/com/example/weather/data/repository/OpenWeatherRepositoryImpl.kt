package com.example.weather.data.repository

import com.example.weather.data.remote.DtoMapper
import com.example.weather.data.remote.NetworkResult
import com.example.weather.data.remote.api.OpenWeatherService
import com.example.weather.domain.models.weather.HistoricalWeather
import com.example.weather.domain.models.weather.Weather
import com.example.weather.domain.repository.OpenWeatherRepository
import javax.inject.Inject

class OpenWeatherRepositoryImpl @Inject constructor(
    private val weatherService: OpenWeatherService,
    private val mapper: DtoMapper
) : OpenWeatherRepository {

    override suspend fun getWeather(
        latitude: Double,
        longitude: Double,
        units: String
    ): NetworkResult<Weather> {
        return try {
            val response = weatherService.getWeather(
                latitude = latitude,
                longitude = longitude,
                units = units.lowercase()
            )
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(mapper.mapDtoToWeather(response.body()!!))
            } else {
                throw RuntimeException("Request error: ${response.message()}")
            }
        } catch (exception: Exception) {
            NetworkResult.Error(exception.message!!)
        }
    }

    override suspend fun getHistoricalWeather(
        latitude: Double,
        longitude: Double,
        units: String,
        dateTime: Long
    ): NetworkResult<HistoricalWeather> {
        return try {
            val response = weatherService.getHistoricalWeather(
                latitude = latitude,
                longitude = longitude,
                units = units.lowercase(),
                dateTime = dateTime
            )
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(mapper.mapDtoToHistoricalWeather(response.body()!!))
            } else {
                throw RuntimeException("Request error: ${response.message()}")
            }
        } catch (exception: Exception) {
            NetworkResult.Error(exception.message!!)
        }
    }
}