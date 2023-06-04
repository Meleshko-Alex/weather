package com.example.weather.data.remote.repository

import android.util.Log
import com.example.weather.data.remote.NetworkResult
import com.example.weather.data.remote.api.AccuWeatherService
import com.example.weather.data.remote.mappers.toCity
import com.example.weather.data.remote.mappers.toFoundCity
import com.example.weather.domain.models.cities.SearchCity
import com.example.weather.domain.models.cities.TopCities
import com.example.weather.domain.repository.AccuWeatherRepository
import javax.inject.Inject

class AccuWeatherRepositoryImpl @Inject constructor(
    private val accuWeatherService: AccuWeatherService
) : AccuWeatherRepository {

    override suspend fun getTopCities(): NetworkResult<TopCities> {
        return try {
            val response = accuWeatherService.getTopCities()
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(
                    TopCities(
                        cities = response.body()!!.map {
                            it.toCity()
                        }
                    )
                )
            } else {
                throw RuntimeException("Request error: " + response.message())
            }
        } catch (exception: Exception) {
            NetworkResult.Error(exception.message!!)
        }
    }

    override suspend fun searchCity(query: String): NetworkResult<SearchCity> {
        return try {
            val response = accuWeatherService.searchCity(query = query)
            if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                Log.d("AccuWeatherRepositoryImpl", "searchCity: successful response")
                NetworkResult.Success(
                    SearchCity(
                        foundCities = response.body()!!.map {
                            it.toFoundCity()
                        }
                    )
                )
            } else {
                Log.d("AccuWeatherRepositoryImpl", "searchCity: unsuccessful response")
                throw RuntimeException("Request error: " + response.message())
            }
        } catch (e: Exception) {
            Log.d("AccuWeatherRepositoryImpl", "searchCity: error")
            NetworkResult.Error(e.message!!)
        }
    }
}