package com.example.weather.data.remote.mappers

import com.example.weather.data.remote.dto.HourlyWeatherDto
import com.example.weather.domain.models.weather.HourlyWeather
import com.example.weather.domain.models.weather.WeatherType

fun HourlyWeatherDto.toCurrentAndHourlyWeather(): HourlyWeather {
    val hourlyWeather = hourly.map { currentWeatherDto ->
        HourlyWeather.CurrentWeather(
            timeDate = currentWeatherDto.forecastedTime,
            temp = currentWeatherDto.temp.toInt(),
            feelsLikeTemperature = currentWeatherDto.feelsLikeTemperature.toInt(),
            humidity = currentWeatherDto.humidity,
            uvi = currentWeatherDto.uvi.toInt(),
            windSpeed = currentWeatherDto.windSpeed,
            weather = WeatherType.toWeatherType(currentWeatherDto.weather[0].id)
        )
    }
    val currentWeather = hourlyWeather[0]
    return HourlyWeather(
        current = currentWeather,
        hourly = hourlyWeather.subList(0, 24) // 24hr range
    )
}