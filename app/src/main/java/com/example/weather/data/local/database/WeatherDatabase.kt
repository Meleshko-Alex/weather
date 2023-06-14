package com.example.weather.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weather.data.local.dao.CitiesDao
import com.example.weather.data.local.dao.DailyWeatherDao
import com.example.weather.data.local.dao.HourlyWeatherDao
import com.example.weather.data.local.entities.CityEntity
import com.example.weather.data.local.entities.OneDayWeatherEntity
import com.example.weather.data.local.entities.OneHourWeatherEntity

/**
 * The main entry point for accessing the local database.
 */
@Database(
    entities = [CityEntity::class, OneHourWeatherEntity::class, OneDayWeatherEntity::class],
    version = 1
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun citiesDao(): CitiesDao
    abstract fun hourlyWeatherDao(): HourlyWeatherDao
    abstract fun dailyWeatherDao(): DailyWeatherDao

    companion object {
        @Volatile
        private var instance: WeatherDatabase? = null

        /**
         * Creates a new instance of the WeatherDatabase or returns the existing instance if it already exists.
         *
         * @param context the application context.
         * @return the WeatherDatabase instance.
         */
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