package com.example.weather.data.network.repository

import com.example.weather.data.network.NetworkResult
import com.example.weather.data.network.WeatherService
import com.example.weather.data.network.mappers.toCurrentAndHourlyWeather
import com.example.weather.domain.models.weather.CurrentAndHourlyWeather
import com.example.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val weatherService: WeatherService) :
    WeatherRepository {

//    override suspend fun getCurrentWeather(
//        latitude: Double,
//        longitude: Double,
//        units: String
//    ): NetworkResult<CurrentWeather> {
//        return try {
//            val response = weatherService.getCurrentWeather(
//                latitude = latitude,
//                longitude = longitude,
//                units = units
//            )
//            if (response.isSuccessful && response.body() != null) {
//                NetworkResult.Success(response.body()!!.toCurrentWeather())
//            } else {
//                throw RuntimeException("Response wasn't successful: ${response.message()}")
//            }
//        } catch (exception: Exception) {
//            exception.printStackTrace()
//            NetworkResult.Error(exception.message ?: "An unknown error occurred")
//        }
//    }
//
//    override suspend fun getWeatherForecast(
//        latitude: Double,
//        longitude: Double,
//        units: String,
//        timestampsCount: Int
//    ): NetworkResult<WeatherForecast> {
//        return try {
//            val response = weatherService.getWeatherForecast(
//                latitude = latitude,
//                longitude =  longitude,
//                units =  units,
//                timestampsCount = timestampsCount
//            )
//            if (response.isSuccessful && response.body() != null) {
////                NetworkResult.Success(response.body()!!.toWeatherForecast())
//            } else {
//                throw RuntimeException("Response wasn't successful: ${response.message()}")
//            }
//        } catch (exception: Exception) {
//            NetworkResult.Error(exception.message!!)
//        }
//    }
//
//    override suspend fun getTodayWeather(
//        latitude: Double,
//        longitude: Double,
//        units: String,
//        timestampsCount: Int
//    ): NetworkResult<WeatherData> {
//        TODO("Not yet implemented")
//    }

    override suspend fun getCurrentAndHourlyWeather(
        latitude: Double,
        longitude: Double,
        units: String
    ): NetworkResult<CurrentAndHourlyWeather> {
        return try {
            val response = weatherService.getCurrentAndHourlyWeather(
                latitude = latitude,
                longitude = longitude,
                units = units
            )
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!.toCurrentAndHourlyWeather())
            } else {
                throw RuntimeException("Response wasn't successful: ${response.message()}")
            }
        } catch (exception: Exception) {
            NetworkResult.Error(exception.message!!)
        }
    }
}