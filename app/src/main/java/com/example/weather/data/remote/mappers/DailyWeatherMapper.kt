package com.example.weather.data.remote.mappers

import com.example.weather.data.remote.dto.DailyWeatherDto
import com.example.weather.domain.models.weather.DailyWeather
import com.example.weather.domain.models.weather.WeatherType

fun DailyWeatherDto.toDailyWeather(): DailyWeather {
    return DailyWeather(
        daily = daily.map {
            DailyWeather.OneDayWeather(
                timeDate = it.forecastedTime,
                minTemp = it.temp.min.toInt(),
                maxTemp = it.temp.max.toInt(),
                weather = WeatherType.toWeatherType(it.weather[0].id)
            )
        }.subList(1, daily.size)
    )
}