package com.example.weather.ui.weather_home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.common.DataStoreManager
import com.example.weather.common.hasInternetConnection
import com.example.weather.data.remote.NetworkResult
import com.example.weather.domain.models.weather.HourlyWeather
import com.example.weather.domain.repository.OpenWeatherRepository
import com.example.weather.ui.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeWeatherViewModel @Inject constructor(
    private val repository: OpenWeatherRepository,
    private val dataStoreManager: DataStoreManager,
    app: Application
) : AndroidViewModel(app) {
    private var _state = MutableLiveData<State<HourlyWeather>>()
    val state: LiveData<State<HourlyWeather>> = _state
    val currentCity = dataStoreManager.getCurrentCity().asLiveData()

    fun getWeatherData(
        latitude: Double,
        longitude: Double,
    ) {
        _state.value = State.Loading()
        viewModelScope.launch {
            when (val result = repository.getHourlyWeather(latitude, longitude)) {
                is NetworkResult.Success -> {
                    _state.postValue(State.Success(result.data!!))
                    // save data to db
                }

                is NetworkResult.Error -> {
                    _state.postValue(State.Error(result.message!!))
                }
            }
        }
    }
}