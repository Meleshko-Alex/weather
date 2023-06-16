package com.example.weather.data.remote.api

import com.example.weather.BuildConfig
import com.example.weather.data.remote.dto.CityDto
import com.example.weather.data.remote.dto.SearchCityResultDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * An interface that defines the API endpoints for retrieving cities data from the AccuWeather API.
 */
interface AccuWeatherService {
    /**
     * Retrieves a list of top cities.
     * @param apiKey the API key for authentication.
     * @return a Response object containing a list of CityDto objects.
     */
    @GET("locations/v1/topcities/150")
    suspend fun getTopCities(
        @Query("apikey") apiKey: String = ACCUWEATHER_API_KEY
    ): Response<List<CityDto>>

    /**
     * Searches for a city based on the provided query.
     * @param apiKey the API key for authentication.
     * @param query the search query.
     * @return a response object containing a list of SearchCityResultDto objects
     */
    @GET("/locations/v1/cities/search")
    suspend fun searchCity(
        @Query("apikey") apiKey: String = ACCUWEATHER_API_KEY,
        @Query("q") query: String
    ): Response<List<SearchCityResultDto>>

    companion object {
        const val ACCUWEATHER_API_KEY = "RN8pDy2Ysog2abMQEVNUJCvAKre612Hd"
    }
}