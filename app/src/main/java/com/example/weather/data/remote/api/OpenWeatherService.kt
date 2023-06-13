package com.example.weather.data.remote.api

import com.example.weather.BuildConfig
import com.example.weather.data.remote.dto.HistoricalWeatherDto
import com.example.weather.data.remote.dto.WeatherDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {

    @GET("onecall")
    suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = BuildConfig.OPENWEATHER_API_KEY,
        @Query("units") units: String,
        @Query("exclude") excludeValues: String = "${Exclude.MINUTELY.value},${Exclude.CURRENT.value},${Exclude.ALERTS.value}"
    ): Response<WeatherDto>

    @GET("onecall/timemachine")
    suspend fun getHistoricalWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = BuildConfig.OPENWEATHER_API_KEY,
        @Query("units") units: String,
        @Query("dt") dateTime: Long
    ): Response<HistoricalWeatherDto>

    private enum class Exclude(val value: String) {
        CURRENT("current"),
        MINUTELY("minutely"),
        HOURLY("hourly"),
        DAILY("daily"),
        ALERTS("alerts")
    }
}