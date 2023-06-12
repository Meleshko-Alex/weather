package com.example.weather.data.repository

import android.util.Log
import com.example.weather.data.remote.DtoMapper
import com.example.weather.data.remote.NetworkResult
import com.example.weather.data.remote.api.AccuWeatherService
import com.example.weather.domain.models.cities.SearchCityResult
import com.example.weather.domain.models.cities.TopCities
import com.example.weather.domain.repository.AccuWeatherRepository
import javax.inject.Inject

class AccuWeatherRepositoryImpl @Inject constructor(
    private val accuWeatherService: AccuWeatherService,
    private val mapper: DtoMapper
) : AccuWeatherRepository {

    override suspend fun getTopCities(): NetworkResult<TopCities> {
        return try {
            val response = accuWeatherService.getTopCities()
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(TopCities(response.body()!!.map { mapper.mapDtoToCity(it) }))
            } else {
                throw RuntimeException("Request error: " + response.message())
            }
        } catch (exception: Exception) {
            NetworkResult.Error(exception.message!!)
        }
    }

    override suspend fun searchCity(query: String): NetworkResult<SearchCityResult> {
        return try {
            val response = accuWeatherService.searchCity(query = query)
            if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                Log.d("AccuWeatherRepositoryImpl", "searchCity: successful response")
                NetworkResult.Success(
                    SearchCityResult(response.body()!!.map { mapper.mapDtoToFoundCity(it) }))
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