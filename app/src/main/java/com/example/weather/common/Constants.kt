package com.example.weather.common

object Constants {
    const val CITIES_TABLE_NAME = "cities"
    const val HOURLY_WEATHER_CACHE_TABLE_NAME = "hourly_weather_cache"
    const val DAILY_WEATHER_CACHE_TABLE_NAME = "daily_weather_cache"

    enum class MeasurementsUnits(val value: String) {
        METRIC("Metric"),
        IMPERIAL("Imperial")
    }
}

