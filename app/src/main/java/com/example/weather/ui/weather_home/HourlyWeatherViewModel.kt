package com.example.weather.ui.weather_home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.remote.NetworkResult
import com.example.weather.domain.models.weather.HourlyWeather
import com.example.weather.domain.repository.OpenWeatherRepository
import com.example.weather.ui.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HourlyWeatherViewModel @Inject constructor(
    private val repository: OpenWeatherRepository
) : ViewModel() {
    private var _state = MutableLiveData<State<HourlyWeather>>()
    val state: LiveData<State<HourlyWeather>> = _state

    private var functionExecuted = false

    fun getWeatherData(
        latitude: Double,
        longitude: Double,
        onRefresh: Boolean = false
    ) {
        if (onRefresh) {
            functionExecuted = false
        }

        if (!functionExecuted) {
            Log.d("ViewModel", "getWeatherData executed")
            _state.value = State.Loading()

            viewModelScope.launch {
                when (val result = repository.getHourlyWeather(latitude, longitude)) {
                    is NetworkResult.Success -> {
                        _state.postValue(State.Success(result.data!!))
                    }

                    is NetworkResult.Error -> {
                        _state.postValue(State.Error(result.message!!))
                    }
                }
            }

            functionExecuted = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        functionExecuted = false
    }
}