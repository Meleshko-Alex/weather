package com.example.weather.domain.repository

import com.example.weather.domain.models.cities.TopCities

interface WeatherDatabaseRepository {

    suspend fun getTopCities(): TopCities
}