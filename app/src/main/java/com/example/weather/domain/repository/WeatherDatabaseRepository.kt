package com.example.weather.domain.repository

import com.example.weather.common.Resource
import com.example.weather.domain.models.cities.TopCities
import com.example.weather.domain.models.weather.DailyWeather
import com.example.weather.domain.models.weather.HourlyWeather

interface WeatherDatabaseRepository {
    /**
     * Retrieves the top cities from the weather database.
     *
     * @return the top cities data.
     */
    suspend fun getTopCities(): Resource<TopCities>

    /**
     * Saves the hourly weather data to the weather database.
     *
     * @param hourlyWeather The hourly weather data to be saved.
     */
    suspend fun saveHourlyWeatherData(hourlyWeather: HourlyWeather)

    /**
     * Retrieves the hourly weather data from the weather database.
     *
     * @return the hourly weather data.
     */
    suspend fun getHourlyWeatherData(): Resource<HourlyWeather>

    /**
     * Saves the daily weather data to the weather database.
     *
     * @param dailyWeather The daily weather data to be saved.
     */

    suspend fun saveDailyWeatherData(dailyWeather: DailyWeather)

    /**
     * Retrieves the daily weather data from the weather database.
     *
     * @return the daily weather data.
     */
    suspend fun getDailyWeatherData(): Resource<DailyWeather>
}