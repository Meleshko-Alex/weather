package com.example.weather.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weather.data.local.dao.CitiesDao
import com.example.weather.data.local.dao.HourlyWeatherDao
import com.example.weather.data.local.entities.CityEntity
import com.example.weather.data.local.entities.OneHourWeatherEntity
import com.example.weather.domain.models.cities.City

@Database(entities = [CityEntity::class, OneHourWeatherEntity::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun citiesDao(): CitiesDao
    abstract fun hourlyWeatherDao(): HourlyWeatherDao

    companion object {

        @Volatile
        private var instance: WeatherDatabase? = null

        fun createDatabase(context: Context): WeatherDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): WeatherDatabase {
            return Room.databaseBuilder(context, WeatherDatabase::class.java, "weather_app.db")
                .createFromAsset("database/weather_app_db.db")
                .build()
        }
    }
}