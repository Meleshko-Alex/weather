package com.example.weather.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weather.data.local.dao.CitiesDao
import com.example.weather.data.local.entities.CityEntity
import com.example.weather.domain.models.cities.City

@Database(entities = [CityEntity::class], version = 1, exportSchema = true)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun citiesDao(): CitiesDao

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