package com.example.weather.data.remote.repository

import com.example.weather.data.local.dao.CitiesDao
import com.example.weather.data.local.mappers.toCity
import com.example.weather.domain.models.cities.TopCities
import com.example.weather.domain.repository.WeatherDatabaseRepository
import javax.inject.Inject

class WeatherDatabaseRepositoryImpl @Inject constructor(
    private val citiesDao: CitiesDao
) : WeatherDatabaseRepository {

    override suspend fun getTopCities(): TopCities {
        return TopCities(
            citiesDao.getAll().map {
                it.toCity()
            }
        )
    }
}