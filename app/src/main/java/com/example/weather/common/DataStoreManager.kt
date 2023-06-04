package com.example.weather.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.weather.domain.models.cities.TopCities
import com.google.gson.Gson
import dagger.hilt.EntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.prefs.Preferences
import javax.inject.Inject

class DataStoreManager(context: Context) {
    private val Context.dataStore by preferencesDataStore(name = Constants.DATASTORE_PREFERENCES_NAME)
    private val dataStore = context.dataStore
    private val gson = Gson()

    suspend fun setCurrentCity(city: TopCities.City) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.CURRENT_CITY] = gson.toJson(city, TopCities.City::class.java)
        }
    }

    fun getCurrentCity(): Flow<TopCities.City> {
        return dataStore.data.map { preferences ->
            val currentCity = preferences[PreferencesKeys.CURRENT_CITY] ?: gson.toJson(Constants.DEFAULT_CITY, TopCities.City::class.java)
            gson.fromJson(currentCity, TopCities.City::class.java)
        }
    }
}