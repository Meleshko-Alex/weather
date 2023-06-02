package com.example.weather.domain.repository

import com.example.weather.data.remote.NetworkResult
import com.example.weather.data.remote.api.AccuWeatherService
import com.example.weather.domain.models.cities.TopCities
import javax.inject.Inject

interface AccuWeatherRepository {

    suspend fun getTopCities(): NetworkResult<TopCities>
}