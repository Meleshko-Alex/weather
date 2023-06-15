package com.example.weather.presentation.user_profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.common.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val unit = dataStoreManager.getMeasurementUnit().asLiveData()

    /**
     * Sets the measurement unit used for weather data
     * The measurement unit is stored in the DataStore.
     *
     * @param unit the measurement unit to be set
     */
    fun setMeasurementUnit(unit: String) {
        viewModelScope.launch {
            dataStoreManager.setMeasurementUnit(unit)
        }
    }
}