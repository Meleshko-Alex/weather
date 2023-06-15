package com.example.weather.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.common.DataStoreManager
import com.example.weather.domain.models.cities.City
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    /**
     * Saves the selected city for the weather data in the DataStore
     *
     * @param city the selected city to be saved
     */
    fun saveSelectedCity(city: City) {
        viewModelScope.launch {
            dataStoreManager.setCurrentCity(city)
        }
    }
}