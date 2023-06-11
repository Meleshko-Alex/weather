package com.example.weather.data.local

import com.example.weather.data.local.entities.CityEntity
import com.example.weather.data.local.entities.OneDayWeatherEntity
import com.example.weather.data.local.entities.OneHourWeatherEntity
import com.example.weather.domain.models.cities.City
import com.example.weather.domain.models.weather.OneDayWeather
import com.example.weather.domain.models.weather.OneHourWeather
import com.example.weather.domain.models.weather.WeatherType

fun CityEntity.toCity(): City {
    return City(
        name = city,
        latitude = latitude,
        longitude = longitude
    )
}

fun OneHourWeatherEntity.toOneHourWeather(): OneHourWeather {
    return OneHourWeather(
        weatherId = weatherId,
        timeDate = timeDate,
        temp = temp,
        feelsLikeTemperature = feelsLikeTemperature,
        humidity = humidity,
        uvi = uvi,
        windSpeed = windSpeed,
        weather = WeatherType.toWeatherType(weatherId)
    )
}

fun OneDayWeatherEntity.toOneDayWeather(): OneDayWeather {
    return OneDayWeather(
        weatherId = weatherId,
        timeDate = timeDate,
        minTemp = minTemp,
        maxTemp = maxTemp,
        weather = WeatherType.toWeatherType(weatherId),
        summary = summary
    )
}
