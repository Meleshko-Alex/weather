package com.example.weather.data.remote

import com.example.weather.data.remote.dto.CityDto
import com.example.weather.data.remote.dto.DailyWeatherDto
import com.example.weather.data.remote.dto.HourlyWeatherDto
import com.example.weather.data.remote.dto.SearchCityResultDto
import com.example.weather.domain.models.cities.City
import com.example.weather.domain.models.cities.SearchCity
import com.example.weather.domain.models.weather.DailyWeather
import com.example.weather.domain.models.weather.HourlyWeather
import com.example.weather.domain.models.weather.OneDayWeather
import com.example.weather.domain.models.weather.OneHourWeather
import com.example.weather.domain.models.weather.WeatherType

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

fun HourlyWeatherDto.toCurrentAndHourlyWeather(): HourlyWeather {
    val hourlyWeather = hourly.map { currentWeatherDto ->
        OneHourWeather(
            weatherId = currentWeatherDto.weather[0].id,
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

fun DailyWeatherDto.toDailyWeather(): DailyWeather {
    return DailyWeather(
        daily = daily.map {
            OneDayWeather(
                weatherId = it.weather[0].id,
                timeDate = it.forecastedTime,
                minTemp = it.temp.min.toInt(),
                maxTemp = it.temp.max.toInt(),
                weather = WeatherType.toWeatherType(it.weather[0].id)
            )
        }.subList(1, daily.size)
    )
}