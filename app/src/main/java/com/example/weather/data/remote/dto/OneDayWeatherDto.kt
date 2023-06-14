package com.example.weather.data.remote.dto

import com.squareup.moshi.Json

/**
 * Represents the DTO for the daily weather data received from the API
 */
data class OneDayWeatherDto(
    @Json(name = "dt") val forecastedTime: Long = 0,
    val sunrise: Int = 0,
    val sunset: Int = 0,
    val moonrise: Int = 0,
    val moonset: Int = 0,
    @Json(name = "moon_phase") val moonPhase: Double = 0.0,
    val summary: String = "",
    val temp: Temp = Temp(),
    @Json(name = "feels_like") val feelsLikeTemperature: FeelsLike = FeelsLike(),
    val pressure: Int = 0,
    val humidity: Int = 0,
    @Json(name = "dew_point") val dewPoint: Double = 0.0,
    @Json(name = "wind_speed") val windSpeed: Double = 0.0,
    @Json(name = "wind_gust") val windGust: Double = 0.0,
    @Json(name = "wind_deg") val windDirectionDegrees: Int = 0,
    val clouds: Int = 0,
    val uvi: Double = 0.0,
    @Json(name = "pop") val precipitationProbability: Double = 0.0,
    val rain: Double = 0.0,
    val snow: Double = 0.0,
    val weather: List<WeatherDataDto> = listOf(),
) {
    /**
     * Represents the DTO for the feels like temperature data.
     */
    data class FeelsLike(
        val morn: Double = 0.0,
        val day: Double = 0.0,
        val eve: Double = 0.0,
        val night: Double = 0.0
    )

    /**
     * Represents the DTO for the temperature data.
     */
    data class Temp(
        val morn: Double = 0.0,
        val day: Double = 0.0,
        val eve: Double = 0.0,
        val night: Double = 0.0,
        val min: Double = 0.0,
        val max: Double = 0.0
    )
}