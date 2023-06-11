package com.example.weather.data.repository

import com.example.weather.data.local.dao.CitiesDao
import com.example.weather.data.local.dao.DailyWeatherDao
import com.example.weather.data.local.dao.HourlyWeatherDao
import com.example.weather.data.local.database.WeatherDatabase
import com.example.weather.data.local.entities.OneHourWeatherEntity
import com.example.weather.data.local.toCity
import com.example.weather.data.local.toOneDayWeather
import com.example.weather.data.local.toOneHourWeather
import com.example.weather.domain.models.cities.TopCities
import com.example.weather.domain.models.weather.DailyWeather
import com.example.weather.domain.models.weather.HourlyWeather
import com.example.weather.domain.repository.WeatherDatabaseRepository
import com.example.weather.domain.toOneDayWeatherEntity
import com.example.weather.domain.toOneHourWeatherEntity
import javax.inject.Inject

class WeatherDatabaseRepositoryImpl @Inject constructor(
    private val database: WeatherDatabase
) : WeatherDatabaseRepository {

    override suspend fun getTopCities(): TopCities {
        return TopCities(database.citiesDao().getAll().map {
            it.toCity()
        })
    }

    override suspend fun saveHourlyWeatherData(hourlyWeather: HourlyWeather) {
        database.hourlyWeatherDao().deleteAllHourlyWeather()
        database.hourlyWeatherDao().insertHourlyWeather(hourlyWeather.hourly.map {
            it.toOneHourWeatherEntity()
        })
    }

    override suspend fun getHourlyWeatherData(): HourlyWeather {
        val data = database.hourlyWeatherDao().getAllHourlyWeather().map {
            it.toOneHourWeather()
        }
        return HourlyWeather(
            current = data[0],
            hourly = data
        )
    }

    override suspend fun saveDailyWeatherData(dailyWeather: DailyWeather) {
        database.dailyWeatherDao().deleteAllDailyWeather()
        database.dailyWeatherDao().insertDailyWeather(dailyWeather.daily.map {
            it.toOneDayWeatherEntity()
        })
    }

    override suspend fun getDailyWeatherData(): DailyWeather {
        return DailyWeather(
            daily = database.dailyWeatherDao().getAllDailyWeather().map {
                it.toOneDayWeather()
            }
        )
    }
}