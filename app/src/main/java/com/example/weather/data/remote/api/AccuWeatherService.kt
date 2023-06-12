package com.example.weather.data.remote.api

import com.example.weather.BuildConfig
import com.example.weather.data.remote.dto.CityDto
import com.example.weather.data.remote.dto.SearchCityResultDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AccuWeatherService {

    @GET("locations/v1/topcities/150")
    suspend fun getTopCities(
        @Query("apikey") apiKey: String = BuildConfig.ACCUWEATHER_API_KEY
    ): Response<List<CityDto>>

    @GET("/locations/v1/cities/search")
    suspend fun searchCity(
        @Query("apikey") apiKey: String = BuildConfig.ACCUWEATHER_API_KEY,
        @Query("q") query: String
    ): Response<List<SearchCityResultDto>>
}