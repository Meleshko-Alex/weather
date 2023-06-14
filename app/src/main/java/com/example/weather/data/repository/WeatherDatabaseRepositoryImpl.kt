package com.example.weather.data.repository

import android.util.Log
import com.example.weather.common.Resource
import com.example.weather.data.local.EntityMapper
import com.example.weather.data.local.database.WeatherDatabase
import com.example.weather.domain.models.cities.TopCities
import com.example.weather.domain.models.weather.DailyWeather
import com.example.weather.domain.models.weather.HourlyWeather
import com.example.weather.domain.repository.WeatherDatabaseRepository
import java.lang.Exception
import javax.inject.Inject

class WeatherDatabaseRepositoryImpl @Inject constructor(
    private val database: WeatherDatabase,
    private val mapper: EntityMapper
) : WeatherDatabaseRepository {
    private val TAG = this.javaClass.simpleName

    override suspend fun getTopCities(): Resource<TopCities> {
        return saveDbCall {
            TopCities(database.citiesDao().getAll().map {
                mapper.mapEntityToCity(it)
            })
        }
    }

    override suspend fun saveHourlyWeatherData(hourlyWeather: HourlyWeather) {
        saveDbCall {
            database.hourlyWeatherDao().apply {
                deleteAllHourlyWeather()
                insertHourlyWeather(hourlyWeather.hourly.map {
                    mapper.mapOneHourWeatherToEntity(it)
                })
            }
        }
    }

    override suspend fun getHourlyWeatherData(): Resource<HourlyWeather> {
        return saveDbCall {
            val data = database.hourlyWeatherDao().getAllHourlyWeather().map {
                mapper.mapEntityToOneHourWeather(it)
            }
            HourlyWeather(
                current = data[0],
                hourly = data
            )
        }
    }

    override suspend fun saveDailyWeatherData(dailyWeather: DailyWeather) {
        saveDbCall {
            database.dailyWeatherDao().apply {
                deleteAllDailyWeather()
                insertDailyWeather(dailyWeather.daily.map {
                    mapper.mapOneDayWeatherToEntity(it)
                })
            }
        }
    }

    override suspend fun getDailyWeatherData(): Resource<DailyWeather> {
        return saveDbCall {
            DailyWeather(
                daily = database.dailyWeatherDao().getAllDailyWeather().map {
                    mapper.mapEntityToOneDayWeather(it)
                }
            )
        }
    }

    private inline fun <T> saveDbCall(func: () -> T): Resource<T> {
        return try {
            Resource.Success(func.invoke())
        } catch (e: Exception) {
            Log.e(TAG, e.printStackTrace().toString())
            Resource.Error(e.message!!)
        }
    }
}