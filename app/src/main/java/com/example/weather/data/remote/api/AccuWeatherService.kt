package com.example.weather.data.remote.api

import com.example.weather.data.remote.dto.CityDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AccuWeatherService {

    @GET("locations/v1/topcities/150")
    suspend fun getTopCities(
        @Query("apikey") apiKey: String = API_KEY
    ): Response<List<CityDto>>

    companion object {
        private const val API_KEY = "RN8pDy2Ysog2abMQEVNUJCvAKre612Hd"
        const val BASE_URL = "https://dataservice.accuweather.com/"
    }
}