package com.example.weather.data.network.mappers

import com.example.weather.data.network.dto.CurrentWeatherDto
import com.example.weather.domain.models.CurrentWeather
import java.time.LocalDateTime


fun CurrentWeatherDto.toCurrentWeather(): CurrentWeather {
    return CurrentWeather(
        cityName = name,
        weather = toWeather(),
        mainInfo = toMainInfo(),
        time = LocalDateTime.now(),
        windSpeed = wind.speed
    )
}

private fun CurrentWeatherDto.toWeather(): CurrentWeather.Weather {
    val weather = weather[0]
    return CurrentWeather.Weather(
        main = weather.main,
        icon = weather.icon
    )
}

private fun CurrentWeatherDto.toMainInfo(): CurrentWeather.MainInfo {
    return CurrentWeather.MainInfo(
        feelsLike = main.feelsLike,
        temperature = main.temperature,
        tempMax = main.tempMax,
        tempMin = main.tempMin
    )
}