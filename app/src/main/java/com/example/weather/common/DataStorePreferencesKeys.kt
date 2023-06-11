package com.example.weather.common

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStorePreferencesKeys {
    val CURRENT_CITY = stringPreferencesKey("current_city")
    val MEASUREMENT_UNITS = stringPreferencesKey("measurement_units")
}