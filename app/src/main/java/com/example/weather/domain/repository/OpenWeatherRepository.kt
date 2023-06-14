package com.example.weather.domain.repository

import com.example.weather.common.Resource
import com.example.weather.domain.models.weather.HistoricalWeather
import com.example.weather.domain.models.weather.Weather

interface OpenWeatherRepository {
    /**
     * Retrieves the current weather for the specified latitude and longitude.
     *
     * @param latitude the latitude of the location.
     * @param longitude the longitude of the location.
     * @param units the units of measurement for the weather data.
     * @return a [Resource] containing the current weather data.
     */
    suspend fun getWeather(
        latitude: Double,
        longitude: Double,
        units: String
    ): Resource<Weather>

    /**
     * Retrieves the historical weather data for the specified latitude, longitude, and date.
     *
     * @param latitude the latitude of the location.
     * @param longitude the longitude of the location.
     * @param units the units of measurement for the weather data.
     * @param dateTime the date and time for which historical weather data is requested.
     * @return a [Resource] containing the historical weather data.
     */
    suspend fun getHistoricalWeather(
        latitude: Double,
        longitude: Double,
        units: String,
        dateTime: Long
    ): Resource<HistoricalWeather>
}