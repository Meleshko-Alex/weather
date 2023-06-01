package com.example.weather.data.network

import com.example.weather.data.network.dto.DailyWeatherDto
import com.example.weather.data.network.dto.HourlyWeatherDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("onecall")
    suspend fun getHourlyWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String,
        @Query("exclude") excludeValues: String = "${Exclude.MINUTELY.value},${Exclude.CURRENT.value},${Exclude.DAILY.value},${Exclude.ALERTS.value}"
    ): Response<HourlyWeatherDto>

    @GET("onecall")
    suspend fun getDailyWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String,
        @Query("exclude") excludeValues: String = "${Exclude.MINUTELY.value},${Exclude.CURRENT.value},${Exclude.HOURLY.value},${Exclude.ALERTS.value}"
    ): Response<DailyWeatherDto>

    companion object {
        const val API_KEY = "124c24ac7ae91b33384bcfd450f0e40c"
        const val BASE_URL = "https://api.openweathermap.org/data/3.0/"

        enum class Exclude(val value: String) {
            CURRENT("current"),
            MINUTELY("minutely"),
            HOURLY("hourly"),
            DAILY("daily"),
            ALERTS("alerts")
        }
    }
}