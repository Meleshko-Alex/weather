package com.example.weather.common

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.weather.domain.models.cities.City
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(context: Context) {
    private val Context.dataStore by preferencesDataStore(name = Constants.DATASTORE_PREFERENCES_NAME)
    private val dataStore = context.dataStore
    private val gson = Gson()

    suspend fun setCurrentCity(city: City) {
        dataStore.edit { preferences ->
            preferences[DataStorePreferencesKeys.CURRENT_CITY] =
                gson.toJson(city, City::class.java)
        }
    }

    fun getCurrentCity(): Flow<City> {
        return dataStore.data.map { preferences ->
            val currentCity = preferences[DataStorePreferencesKeys.CURRENT_CITY]
                ?: gson.toJson(
                    Constants.DEFAULT_CITY,
                    City::class.java
                )
            gson.fromJson(currentCity, City::class.java)
        }
    }

    fun getUserPref(): Flow<UserPref> {
        return dataStore.data.map { preferences ->
            val currentCity = preferences[DataStorePreferencesKeys.CURRENT_CITY]
                ?: gson.toJson(
                    Constants.DEFAULT_CITY,
                    City::class.java
                )
            val unit =
                preferences[DataStorePreferencesKeys.MEASUREMENT_UNITS] ?: Constants.MeasurementsUnits.METRIC.value
            UserPref(
                city = gson.fromJson(currentCity, City::class.java),
                measurementUnit = unit
            )
        }
    }

    suspend fun setMeasurementUnit(unit: String) {
        dataStore.edit { preferences ->
            preferences[DataStorePreferencesKeys.MEASUREMENT_UNITS] = unit
        }
    }

    fun getMeasurementUnit(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[DataStorePreferencesKeys.MEASUREMENT_UNITS] ?: Constants.MeasurementsUnits.METRIC.value
        }
    }
}