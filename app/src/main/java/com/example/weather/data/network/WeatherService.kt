package com.example.weather.data.network

import com.example.weather.data.network.dto.CurrentWeatherDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = Units.METRIC.name,
        @Query("appid") apiKey: String = API_KEY
    ): Response<CurrentWeatherDto>

    companion object {
        enum class Units(name: String) {
            KELVIN("standard"),
            METRIC("metric"),
            IMPERIAL("imperial")
        }
        const val API_KEY = "124c24ac7ae91b33384bcfd450f0e40c"
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }
}