package com.example.weather.common

import androidx.datastore.preferences.core.stringPreferencesKey
import com.squareup.moshi.Moshi

object PreferencesKeys {
    val CURRENT_CITY = stringPreferencesKey("current_city")
    val HOURLY_WEATHER = stringPreferencesKey("hourly_weather_cache")
    val DAILY_WEATHER = stringPreferencesKey("daily_weather_cache")
}