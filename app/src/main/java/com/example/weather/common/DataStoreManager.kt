package com.example.weather.common

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.weather.domain.models.cities.City
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


/**
 * Manages the data stored in DataStore for user preferences.
 *
 * @param context the application context.
 */
class DataStoreManager(context: Context) {
    private val Context.dataStore by preferencesDataStore(name = DATASTORE_PREFERENCES_NAME)
    private val dataStore = context.dataStore
    private val gson = Gson()

    /**
     * Sets the current city in DataStore.
     *
     * @param city the city to set as the current city.
     */
    suspend fun setCurrentCity(city: City) {
        dataStore.edit { preferences ->
            preferences[DataStorePreferencesKeys.CURRENT_CITY] = gson.toJson(city, City::class.java)
        }
    }

    /**
     * Retrieves the current city from DataStore.
     *
     * @return a [Flow] emitting the current city.
     */
    fun getCurrentCity(): Flow<City> {
        return dataStore.data.map { preferences ->
            val currentCity = preferences[DataStorePreferencesKeys.CURRENT_CITY]
                ?: DEFAULT_CITY
            gson.fromJson(currentCity, City::class.java)
        }
    }

    /**
     * Retrieves the user preferences from DataStore.
     *
     * @return a [Flow] emitting the user preferences.
     */
    fun getUserPref(): Flow<UserPref> {
        return dataStore.data.map { preferences ->
            val currentCity = preferences[DataStorePreferencesKeys.CURRENT_CITY]
                ?: DEFAULT_CITY
            val unit =
                preferences[DataStorePreferencesKeys.MEASUREMENT_UNITS]
                    ?: Constants.MeasurementsUnits.METRIC.value
            UserPref(
                city = gson.fromJson(currentCity, City::class.java),
                measurementUnit = unit
            )
        }
    }

    /**
     * Sets the measurement unit in DataStore.
     *
     * @param unit the measurement unit to set.
     */
    suspend fun setMeasurementUnit(unit: String) {
        dataStore.edit { preferences ->
            preferences[DataStorePreferencesKeys.MEASUREMENT_UNITS] = unit
        }
    }

    /**
     * Retrieves the measurement unit from DataStore.
     *
     * @return a [Flow] emitting the measurement unit.
     */
    fun getMeasurementUnit(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[DataStorePreferencesKeys.MEASUREMENT_UNITS]
                ?: Constants.MeasurementsUnits.METRIC.value
        }
    }

    companion object {
        private val DEFAULT_CITY = Gson().toJson(City("Zaporizhzhia", 47.8378, 35.1383))
        private const val DATASTORE_PREFERENCES_NAME = "settings"
    }

}