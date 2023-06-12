package com.example.weather.data.remote.dto

import com.squareup.moshi.Json

data class WeatherDto(
    @Json(name = "lat") val latitude: Double = 0.0,
    @Json(name = "lon") val longitude: Double = 0.0,
    val timezone: String = "",
    @Json(name = "timezone_offset") val timezoneOffset: Int = 0,
    val hourly: List<OneHourWeatherDto> = listOf(),
    val daily: List<OneDayWeatherDto> = listOf()
) {

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

    data class WeatherDataDto(
        val description: String = "",
        val icon: String = "",
        val id: Int = 0,
        val main: String = ""
    )
}