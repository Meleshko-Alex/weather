package com.example.weather.domain.repository

import com.example.weather.domain.models.cities.TopCities
import com.example.weather.domain.models.weather.HourlyWeather

interface WeatherDatabaseRepository {

    suspend fun getTopCities(): TopCities
    suspend fun saveHourlyWeatherData(hourlyWeather: HourlyWeather)
    suspend fun getHourlyWeatherData(): HourlyWeather
}