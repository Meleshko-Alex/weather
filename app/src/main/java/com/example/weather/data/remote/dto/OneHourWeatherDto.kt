package com.example.weather.data.remote.dto

import com.squareup.moshi.Json

data class OneHourWeatherDto(
    @Json(name = "dt") val forecastedTime: Long = 0,
    val temp: Double = 0.0,
    @Json(name = "feels_like") val feelsLikeTemperature: Double = 0.0,
    val pressure: Int = 0,
    val humidity: Int = 0,
    @Json(name = "dew_point") val dewPoint: Double = 0.0,
    val clouds: Int = 0,
    val uvi: Double = 0.0,
    val visibility: Int = 0,
    @Json(name = "wind_speed") val windSpeed: Double = 0.0,
    @Json(name = "wind_gust") val windGust: Double = 0.0,
    @Json(name = "wind_deg") val windDirectionDegrees: Int = 0,
    val rain: RainDto = RainDto(),
    val snow: SnowDto = SnowDto(),
    val weather: List<WeatherDataDto> = listOf(),
    @Json(name = "pop") val precipitationProbability: Double = 0.0
) {
    data class RainDto(
        @Json(name = "1h") val rainVolume1Hour: Double = 0.0
    )

    data class SnowDto(
        @Json(name = "1h") val snowVolume1Hour: Double = 0.0
    )
}