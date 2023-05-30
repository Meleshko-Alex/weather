package com.example.weather.data.network.dto

import com.squareup.moshi.Json

data class CurrentWeatherDto(
    @Json(name = "coord") val coordinates: CoordinatesDto = CoordinatesDto(),
    val weather: List<WeatherDto> = listOf(),
    val base: String = "",
    val main: MainDto = MainDto(),
    val visibility: Int = 0,
    val wind: WindDto = WindDto(),
    val clouds: CloudsDto = CloudsDto(),
    val rain: RainDto = RainDto(),
    val snow: SnowDto = SnowDto(),
    @Json(name = "dt") val dataCalculationTime: Int = 0,
    val sys: SysDto = SysDto(),
    val timezone: Int = 0,
    val id: Int = 0,
    val name: String = "",
    val cod: Int = 0
) {
    data class CloudsDto(
        val all: Int = 0
    )

    data class RainDto(
        @Json(name = "1h") val rainVolume1Hour: Double = 0.0,
        @Json(name = "3h") val rainVolume3Hour: Double = 0.0
    )

    data class SnowDto(
        @Json(name = "1h") val snowVolume1Hour: Double = 0.0,
        @Json(name = "3h") val snowVolume3Hour: Double = 0.0
    )

    data class CoordinatesDto(
        @Json(name = "lat") val latitude: Double = 0.0,
        @Json(name = "lon") val longitude: Double = 0.0
    )

    data class MainDto(
        @Json(name = "feels_like") val feelsLike: Double = 0.0,
        @Json(name = "grnd_level") val groundLevel: Int = 0,
        val humidity: Int = 0,
        val pressure: Int = 0,
        @Json(name = "sea_level") val seaLevel: Int = 0,
        @Json(name = "temp") val temperature: Double = 0.0,
        @Json(name = "temp_max") val tempMax: Double = 0.0,
        @Json(name = "temp_min") val tempMin: Double = 0.0
    )

    data class SysDto(
        val country: String = "",
        val id: Int = 0,
        val sunrise: Int = 0,
        val sunset: Int = 0,
        val type: Int = 0
    )

    data class WeatherDto(
        val description: String = "",
        val icon: String = "",
        val id: Int = 0,
        val main: String = ""
    )

    data class WindDto(
        @Json(name = "deg") val directionDegrees: Int = 0,
        val gust: Double = 0.0,
        val speed: Double = 0.0
    )
}