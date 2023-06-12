package com.example.weather.data.repository

import com.example.weather.data.local.EntityMapper
import com.example.weather.data.local.database.WeatherDatabase
import com.example.weather.domain.models.cities.TopCities
import com.example.weather.domain.models.weather.DailyWeather
import com.example.weather.domain.models.weather.HourlyWeather
import com.example.weather.domain.repository.WeatherDatabaseRepository
import javax.inject.Inject

class WeatherDatabaseRepositoryImpl @Inject constructor(
    private val database: WeatherDatabase,
    private val mapper: EntityMapper
) : WeatherDatabaseRepository {

    override suspend fun getTopCities(): TopCities {
        return TopCities(database.citiesDao().getAll().map {
            mapper.mapEntityToCity(it)
        })
    }

    override suspend fun saveHourlyWeatherData(hourlyWeather: HourlyWeather) {
        database.hourlyWeatherDao().deleteAllHourlyWeather()
        database.hourlyWeatherDao().insertHourlyWeather(hourlyWeather.hourly.map {
            mapper.mapOneHourWeatherToEntity(it)
        })
    }

    override suspend fun getHourlyWeatherData(): HourlyWeather {
        val data = database.hourlyWeatherDao().getAllHourlyWeather().map {
            mapper.mapEntityToOneHourWeather(it)
        }
        return HourlyWeather(
            current = data[0],
            hourly = data
        )
    }

    override suspend fun saveDailyWeatherData(dailyWeather: DailyWeather) {
        database.dailyWeatherDao().deleteAllDailyWeather()
        database.dailyWeatherDao().insertDailyWeather(dailyWeather.daily.map {
            mapper.mapOneDayWeatherToEntity(it)
        })
    }

    override suspend fun getDailyWeatherData(): DailyWeather {
        return DailyWeather(
            daily = database.dailyWeatherDao().getAllDailyWeather().map {
                mapper.mapEntityToOneDayWeather(it)
            }
        )
    }
}