package com.example.weather.data.repository

import android.util.Log
import com.example.weather.data.remote.DtoMapper
import com.example.weather.common.Resource
import com.example.weather.data.remote.api.AccuWeatherService
import com.example.weather.domain.models.cities.SearchCityResult
import com.example.weather.domain.models.cities.TopCities
import com.example.weather.domain.repository.AccuWeatherRepository
import javax.inject.Inject

class AccuWeatherRepositoryImpl @Inject constructor(
    private val accuWeatherService: AccuWeatherService,
    private val mapper: DtoMapper
) : AccuWeatherRepository {

    override suspend fun getTopCities(): Resource<TopCities> {
        return try {
            val response = accuWeatherService.getTopCities()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(TopCities(response.body()!!.map { mapper.mapDtoToCity(it) }))
            } else {
                throw RuntimeException("Request error: " + response.message())
            }
        } catch (exception: Exception) {
            Resource.Error(exception.message!!)
        }
    }

    override suspend fun searchCity(query: String): Resource<SearchCityResult> {
        return try {
            val response = accuWeatherService.searchCity(query = query)
            if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                Log.d("AccuWeatherRepositoryImpl", "searchCity: successful response")
                Resource.Success(
                    SearchCityResult(response.body()!!.map { mapper.mapDtoToFoundCity(it) }))
            } else {
                Log.d("AccuWeatherRepositoryImpl", "searchCity: unsuccessful response")
                throw RuntimeException("Request error: " + response.message())
            }
        } catch (e: Exception) {
            Log.d("AccuWeatherRepositoryImpl", "searchCity: error")
            Resource.Error(e.message!!)
        }
    }
}