package com.example.weather.common

import com.example.weather.domain.models.cities.City

object Constants {
    const val NIGHT_TIME = 21 // constant represents 21:00 time and is used for choosing a day/night icon
    const val DAY_TIME = 6 // constant represent 06:00 time and is used for choosing a day/night icon
    const val DATASTORE_PREFERENCES_NAME = "settings"
    val DEFAULT_CITY = City("Zaporizhzhia", 47.8378, 35.1383)
    const val CITIES_TABLENAME = "cities"
    const val HOURLYWEATHERCACHE_TABLENAME = "hourly_weather_cache"
    const val DAILYWEATHERCACHE_TABLENAME = "daily_weather_cache"
}