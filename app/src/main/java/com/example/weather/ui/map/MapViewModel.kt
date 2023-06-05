package com.example.weather.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.common.DataStoreManager
import com.example.weather.domain.models.cities.TopCities
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    fun saveSelectedCity(city: TopCities.City) {
        viewModelScope.launch {
            dataStoreManager.setCurrentCity(city)
        }
    }
}