package com.example.weather.common

import com.example.weather.domain.models.cities.City

object Constants {
    const val NIGHT_TIME = 21 // constant represents time - 21:00 - and is used for choosing a day/night icon
    const val DAY_TIME = 6 // constant represents time - 06:00 - and is used for choosing a day/night icon
    const val DATASTORE_PREFERENCES_NAME = "settings"
    val DEFAULT_CITY = City("Zaporizhzhia", 47.8378, 35.1383) // City set by default in data store preferences
    const val MEASURE_UNIT_METRIC = "Metric"
    const val MEASURE_UNIT_IMPERIAL = "Imperial"
    const val CITIES_TABLENAME = "cities"
    const val HOURLYWEATHERCACHE_TABLENAME = "hourly_weather_cache"
    const val DAILYWEATHERCACHE_TABLENAME = "daily_weather_cache"
    const val SPLAHSCREEN_DISPLAYING_TIME = 1500L
    const val SOURCE_URL = "https://openweathermap.org/"
    const val NOTIFICATION_CHANNEL_ID = "weather_report"
    const val NOTIFICATION_CHANNEL_NAME = "Weather reports"
    const val SHARED_PREF = "weather_shared_pref"
}