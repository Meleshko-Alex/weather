package com.example.weather.data.remote

import com.example.weather.data.remote.dto.CityDto
import com.example.weather.data.remote.dto.SearchCityResultDto
import com.example.weather.data.remote.dto.WeatherDto
import com.example.weather.domain.models.cities.City
import com.example.weather.domain.models.cities.SearchCityResult
import com.example.weather.domain.models.weather.OneDayWeather
import com.example.weather.domain.models.weather.OneHourWeather
import com.example.weather.domain.models.weather.Weather
import com.example.weather.domain.models.weather.WeatherType

class DtoMapper {
    fun mapDtoToWeather(model: WeatherDto): Weather {
        val hourly = model.hourly.map {
            mapDtoToOneHourWeather(it)
        }
        val daily = model.daily.map{
            mapDtoToOneDayWeather(it)
        }
        return Weather(
            current = hourly[0],
            hourly = hourly.subList(0, 24),
            daily = daily.subList(1, daily.size)
        )
    }

    private fun mapDtoToOneHourWeather(model: WeatherDto.OneHourWeatherDto): OneHourWeather {
        return OneHourWeather(
            weatherId = model.weather[0].id,
            timeDate = model.forecastedTime,
            temp = model.temp.toInt(),
            feelsLikeTemperature = model.feelsLikeTemperature.toInt(),
            humidity = model.humidity,
            uvi = model.uvi.toInt(),
            windSpeed = model.windSpeed,
            weather = WeatherType.toWeatherType(model.weather[0].id)
        )
    }

    private fun mapDtoToOneDayWeather(model: WeatherDto.OneDayWeatherDto): OneDayWeather {
        return OneDayWeather(
            weatherId = model.weather[0].id,
            timeDate = model.forecastedTime,
            minTemp = model.temp.min.toInt(),
            maxTemp = model.temp.max.toInt(),
            weather = WeatherType.toWeatherType(model.weather[0].id),
            summary = model.summary,
        )
    }

    fun mapDtoToCity(model: CityDto): City {
        return City(
            name = model.englishName,
            latitude = model.geoPosition.latitude,
            longitude = model.geoPosition.longitude
        )
    }

    fun mapDtoToFoundCity(model: SearchCityResultDto): SearchCityResult.FoundCity {
        return SearchCityResult.FoundCity(
            cityName = model.englishName,
            adminAreaName = model.adminArea.englishName,
            countryName = model.country.englishName,
            latitude = model.geoPosition.latitude,
            longitude = model.geoPosition.longitude
        )
    }
}