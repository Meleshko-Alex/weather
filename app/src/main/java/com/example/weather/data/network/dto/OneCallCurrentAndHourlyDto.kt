package com.example.weather.data.network.dto

import com.squareup.moshi.Json

data class OneCallCurrentAndHourlyDto(
    @Json(name = "lat") val latitude: Double = 0.0,
    @Json(name = "lon") val longitude: Double = 0.0,
    val timezone: String = "",
    @Json(name = "timezone_offset") val timezoneOffset: Int = 0,
    val current: CurrentWeatherDto = CurrentWeatherDto(),
    val hourly: List<CurrentWeatherDto> = listOf()
) {
    data class CurrentWeatherDto(
        @Json(name = "dt") val currentTime: Long = 0,
        val sunrise: Int = 0,
        val sunset: Int = 0,
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
        val weather: List<WeatherDto> = listOf(),
    ) {
        data class RainDto(
            @Json(name = "1h") val rainVolume1Hour: Double = 0.0
        )

        data class SnowDto(
            @Json(name = "1h") val snowVolume1Hour: Double = 0.0
        )

        data class WeatherDto(
            val description: String = "",
            val icon: String = "",
            val id: Int = 0,
            val main: String = ""
        )
    }

//    data class HourlyWeatherDto(
//        @Json(name = "dt") val forecastedDataTime: Int = 0,
//        val temp: Double = 0.0,
//        @Json(name = "feels_like") val feelsLikeTemperature: Double = 0.0,
//        val pressure: Int = 0,
//        val humidity: Int = 0,
//        @Json(name = "dew_point") val dewPoint: Double = 0.0,
//        val uvi: Double = 0.0,
//        val clouds: Int = 0,
//        val visibility: Int = 0,
//        @Json(name = "wind_speed") val windSpeed: Double = 0.0,
//        @Json(name = "wind_gust") val windGust: Double = 0.0,
//        @Json(name = "wind_deg") val windDirectionDegrees: Int = 0,
//        @Json(name = "pop") val precipitationProbability: Double = 0.0,
//        val rain: RainDto = RainDto(),
//        val snow: SnowDto = SnowDto(),
//        val weather: List<WeatherDto> = listOf(),
//    )


}