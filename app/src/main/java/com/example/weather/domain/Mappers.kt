package com.example.weather.domain

import com.example.weather.data.local.entities.OneDayWeatherEntity
import com.example.weather.data.local.entities.OneHourWeatherEntity
import com.example.weather.domain.models.weather.OneDayWeather
import com.example.weather.domain.models.weather.OneHourWeather
import com.example.weather.domain.models.weather.WeatherType

fun OneHourWeather.toOneHourWeatherEntity(): OneHourWeatherEntity {
    return OneHourWeatherEntity(
        weatherId = weatherId,
        timeDate = timeDate,
        temp = temp,
        feelsLikeTemperature = feelsLikeTemperature,
        humidity = humidity,
        uvi = uvi,
        windSpeed = windSpeed
    )
}

fun OneDayWeather.toOneDayWeatherEntity(): OneDayWeatherEntity {
    return OneDayWeatherEntity(
        weatherId = weatherId,
        timeDate = timeDate,
        minTemp = minTemp,
        maxTemp = maxTemp
    )
}