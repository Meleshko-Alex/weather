package com.example.weather.domain.repository

import com.example.weather.data.remote.NetworkResult
import com.example.weather.domain.models.cities.SearchCityResult
import com.example.weather.domain.models.cities.TopCities

interface AccuWeatherRepository {

    suspend fun getTopCities(): NetworkResult<TopCities>

    suspend fun searchCity(query: String): NetworkResult<SearchCityResult>
}