package com.example.weather.data.remote.dto

import com.squareup.moshi.Json

data class DailyWeatherDto(
    @Json(name = "lat") val latitude: Double = 0.0,
    @Json(name = "lon") val longitude: Double = 0.0,
    val timezone: String = "",
    @Json(name = "timezone_offset") val timezoneOffset: Int = 0,
    val daily: List<OneDayWeatherDto> = listOf()
) {
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
        val weather: List<WeatherDto> = listOf(),
    ) {
        data class FeelsLike(
            val morn: Double = 0.0,
            val day: Double = 0.0,
            val eve: Double = 0.0,
            val night: Double = 0.0
        )

        data class Temp(
            val morn: Double = 0.0,
            val day: Double = 0.0,
            val eve: Double = 0.0,
            val night: Double = 0.0,
            val min: Double = 0.0,
            val max: Double = 0.0
        )
    }
}