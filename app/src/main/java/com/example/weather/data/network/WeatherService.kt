package com.example.weather.data.network

import com.example.weather.data.network.dto.OneCallCurrentAndHourlyDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

//    @GET("weather")
//    suspend fun getCurrentWeather(
//        @Query("lat") latitude: Double,
//        @Query("lon") longitude: Double,
//        @Query("units") units: String,
//        @Query("appid") apiKey: String = API_KEY
//    ): Response<CurrentWeatherDto>
//
//    @GET("forecast")
//    suspend fun getWeatherForecast(
//        @Query("lat") latitude: Double,
//        @Query("lon") longitude: Double,
//        @Query("appid") apiKey: String = API_KEY,
//        @Query("cnt") timestampsCount: Int,
//        @Query("units") units: String
//    ): Response<WeatherForecastDto>

    @GET("onecall")
    suspend fun getCurrentAndHourlyWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String,
        @Query("exclude") excludeValues: String = "${EXCLUDE.MINUTELY.name},${EXCLUDE.DAILY.name},${EXCLUDE.ALERTS.name}"
    ): Response<OneCallCurrentAndHourlyDto>

    companion object {
        const val API_KEY = "124c24ac7ae91b33384bcfd450f0e40c"
        const val BASE_URL = "https://api.openweathermap.org/data/3.0/"

        enum class EXCLUDE(name: String) {
            CURRENT("current"),
            MINUTELY("minutely"),
            HOURLY("hourly"),
            DAILY("daily"),
            ALERTS("alerts")
        }
    }
}