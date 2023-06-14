package com.example.weather.domain.repository

import com.example.weather.common.Resource
import com.example.weather.domain.models.cities.SearchCityResult
import com.example.weather.domain.models.cities.TopCities

interface AccuWeatherRepository {
    /**
     * Retrieves the list of top cities.
     *
     * @return a [Resource] containing the list of top cities.
     */
    suspend fun getTopCities(): Resource<TopCities>

    /**
     * Searches for a city based on the provided query.
     *
     * @param query the search query.
     * @return a [Resource] containing the search result.
     */
    suspend fun searchCity(query: String): Resource<SearchCityResult>
}