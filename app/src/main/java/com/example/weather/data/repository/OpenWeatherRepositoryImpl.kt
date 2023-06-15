package com.example.weather.data.repository

import android.util.Log
import com.example.weather.data.remote.DtoMapper
import com.example.weather.common.Resource
import com.example.weather.data.remote.api.OpenWeatherService
import com.example.weather.domain.models.weather.HistoricalWeather
import com.example.weather.domain.models.weather.Weather
import com.example.weather.domain.repository.OpenWeatherRepository
import com.example.weather.presentation.State
import java.util.Calendar
import java.util.TreeMap
import javax.inject.Inject

class OpenWeatherRepositoryImpl @Inject constructor(
    private val weatherService: OpenWeatherService,
    private val mapper: DtoMapper
) : OpenWeatherRepository {
    private val TAG = this.javaClass.simpleName

    override suspend fun getWeather(
        latitude: Double,
        longitude: Double,
        units: String
    ): Resource<Weather> {
        return try {
            val response = weatherService.getWeather(
                latitude = latitude,
                longitude = longitude,
                units = units.lowercase()
            )
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(mapper.mapDtoToWeather(response.body()!!))
            } else {
                throw RuntimeException("Request error: ${response.message()}")
            }
        } catch (exception: Exception) {
            Resource.Error(exception.message!!)
        }
    }

    override suspend fun getHistoricalWeather(
        latitude: Double,
        longitude: Double,
        units: String,
        dateTime: Long
    ): Resource<HistoricalWeather> {
        return try {
            val response = weatherService.getHistoricalWeather(
                latitude = latitude,
                longitude = longitude,
                units = units.lowercase(),
                dateTime = dateTime
            )
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(mapper.mapDtoToHistoricalWeather(response.body()!!))
            } else {
                throw RuntimeException("Request error: ${response.message()}")
            }
        } catch (exception: Exception) {
            Log.d(TAG, exception.message.toString())
            Resource.Error(exception.message!!)
        }
    }

    override suspend fun getHistoricalWeatherRange(
        latitude: Double,
        longitude: Double,
        units: String,
        startDate: Calendar,
        endDate: Calendar
    ): Resource<List<HistoricalWeather>> {
        return try {
            val list = mutableListOf<HistoricalWeather>()
            while(startDate <= endDate) {
                when (val result = getHistoricalWeather(latitude, longitude, units, startDate.timeInMillis / 1000)) {
                    is Resource.Success -> {
                        list.add(result.data!!)
                    }

                    is Resource.Error -> {
                        Log.e(TAG, result.message!!)
                        throw RuntimeException(result.message)
                    }
                }
                startDate.add(Calendar.DATE, 1)
            }
            Resource.Success(list)
        } catch (e: Exception) {
            Resource.Error(e.message!!)
        }

    }
}