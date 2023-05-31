package com.example.weather.data.network.mappers

import com.example.weather.data.network.dto.OneCallCurrentAndHourlyDto
import com.example.weather.domain.models.weather.CurrentAndHourlyWeather
import com.example.weather.domain.models.weather.WeatherType

fun OneCallCurrentAndHourlyDto.toCurrentAndHourlyWeather(): CurrentAndHourlyWeather {
    val hourlyWeather = hourly.map { currentWeatherDto ->
        CurrentAndHourlyWeather.CurrentWeather(
            time = currentWeatherDto.currentTime,
            temp = currentWeatherDto.temp,
            feelsLikeTemperature = currentWeatherDto.feelsLikeTemperature,
            humidity = currentWeatherDto.humidity,
            uvi = currentWeatherDto.uvi,
            windSpeed = currentWeatherDto.windSpeed,
            weather = WeatherType.toWeatherType(currentWeatherDto.weather[0].id)
        )
    }
    val currentWeather = hourlyWeather[0]
    return CurrentAndHourlyWeather(
        current = currentWeather,
        hourly = hourlyWeather
    )
}