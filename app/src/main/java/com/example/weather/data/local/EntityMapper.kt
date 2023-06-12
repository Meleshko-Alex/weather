package com.example.weather.data.local

import com.example.weather.data.local.entities.CityEntity
import com.example.weather.data.local.entities.OneDayWeatherEntity
import com.example.weather.data.local.entities.OneHourWeatherEntity
import com.example.weather.domain.models.cities.City
import com.example.weather.domain.models.weather.OneDayWeather
import com.example.weather.domain.models.weather.OneHourWeather
import com.example.weather.domain.models.weather.WeatherType

class EntityMapper {

    fun mapEntityToCity(model: CityEntity): City {
        return City(
            name = model.city,
            latitude = model.latitude,
            longitude = model.longitude
        )
    }

    fun mapEntityToOneHourWeather(model: OneHourWeatherEntity): OneHourWeather {
        return OneHourWeather(
            weatherId = model.weatherId,
            timeDate = model.timeDate,
            temp = model.temp,
            feelsLikeTemperature = model.feelsLikeTemperature,
            humidity = model.humidity,
            uvi = model.uvi,
            windSpeed = model.windSpeed,
            weather = WeatherType.toWeatherType(model.weatherId)
        )
    }

    fun mapOneHourWeatherToEntity(model: OneHourWeather): OneHourWeatherEntity {
        return OneHourWeatherEntity(
            weatherId = model.weatherId,
            timeDate = model.timeDate,
            temp = model.temp,
            feelsLikeTemperature = model.feelsLikeTemperature,
            humidity = model.humidity,
            uvi = model.uvi,
            windSpeed = model.windSpeed
        )
    }

    fun mapEntityToOneDayWeather(model: OneDayWeatherEntity): OneDayWeather {
        return OneDayWeather(
            weatherId = model.weatherId,
            timeDate = model.timeDate,
            minTemp = model.minTemp,
            maxTemp = model.maxTemp,
            weather = WeatherType.toWeatherType(model.weatherId),
            summary = model.summary
        )
    }

    fun mapOneDayWeatherToEntity(model: OneDayWeather): OneDayWeatherEntity {
        return OneDayWeatherEntity(
            weatherId = model.weatherId,
            timeDate = model.timeDate,
            minTemp = model.minTemp,
            maxTemp = model.maxTemp,
            summary = model.summary
        )
    }
}