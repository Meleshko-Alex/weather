package com.example.weather.data.remote.api

import com.example.weather.BuildConfig
import com.example.weather.data.remote.dto.HistoricalWeatherDto
import com.example.weather.data.remote.dto.WeatherDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * An interface that defines the API endpoints for retrieving weather data from the OpenWeather API.
 */
interface OpenWeatherService {
    /**
     * Retrieves the current weather and forecast for the specified latitude and longitude.
     * @param latitude the latitude of the location.
     * @param longitude the longitude of the location.
     * @param apiKey the API key for authentication.
     * @param units the units of measurement for the weather data.
     * @param excludeValues the data blocks to exclude from the response.
     * @return a response object containing the WeatherDto.
     */
    @GET("onecall")
    suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = BuildConfig.OPENWEATHER_API_KEY,
        @Query("units") units: String,
        @Query("exclude") excludeValues: String = "${Exclude.MINUTELY.value},${Exclude.CURRENT.value},${Exclude.ALERTS.value}"
    ): Response<WeatherDto>

    /**
     * Retrieves the historical weather data for the specified latitude, longitude, and date/time.
     * @param latitude the latitude of the location.
     * @param longitude the longitude of the location.
     * @param apiKey the API key for authentication.
     * @param units the units of measurement for the weather data.
     * @param dateTime the date/time in Unix timestamp format.
     * @return a response object containing the HistoricalWeatherDto.
     */
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