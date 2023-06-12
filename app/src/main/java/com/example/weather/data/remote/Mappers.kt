package com.example.weather.data.remote

import com.example.weather.data.remote.dto.CityDto
import com.example.weather.data.remote.dto.OneDayWeatherDto
import com.example.weather.data.remote.dto.OneHourWeatherDto
import com.example.weather.data.remote.dto.SearchCityResultDto
import com.example.weather.data.remote.dto.WeatherDto
import com.example.weather.domain.models.cities.City
import com.example.weather.domain.models.cities.SearchCity
import com.example.weather.domain.models.weather.OneDayWeather
import com.example.weather.domain.models.weather.OneHourWeather
import com.example.weather.domain.models.weather.Weather
import com.example.weather.domain.models.weather.WeatherType

fun WeatherDto.toWeather(): Weather {
    val hourly = hourly.map {
        it.toOneHourWeather()
    }
    val daily = daily.map{
        it.toOneDayWeather()
    }
    return Weather(
        current = hourly[0],
        hourly = hourly.subList(0, 24),
        daily = daily.subList(1, daily.size)
    )
}

fun OneHourWeatherDto.toOneHourWeather(): OneHourWeather {
    return OneHourWeather(
        weatherId = weather[0].id,
        timeDate = forecastedTime,
        temp = temp.toInt(),
        feelsLikeTemperature = feelsLikeTemperature.toInt(),
        humidity = humidity,
        uvi = uvi.toInt(),
        windSpeed = windSpeed,
        weather = WeatherType.toWeatherType(weather[0].id)
    )
}

fun OneDayWeatherDto.toOneDayWeather(): OneDayWeather {
    return OneDayWeather(
        weatherId = weather[0].id,
        timeDate = forecastedTime,
        minTemp = temp.min.toInt(),
        maxTemp = temp.max.toInt(),
        weather = WeatherType.toWeatherType(weather[0].id),
        summary = summary,
    )
}

fun CityDto.toCity(): City {
    return City(
        name = englishName,
        latitude = geoPosition.latitude,
        longitude = geoPosition.longitude
    )
}

fun SearchCityResultDto.toFoundCity(): SearchCity.FoundCity {
    return SearchCity.FoundCity(
        cityName = englishName,
        adminAreaName = adminArea.englishName,
        countryName = country.englishName,
        latitude = geoPosition.latitude,
        longitude = geoPosition.longitude
    )
}