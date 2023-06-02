package com.example.weather.ui.weather_daily

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.remote.NetworkResult
import com.example.weather.domain.models.weather.DailyWeather
import com.example.weather.domain.repository.OpenWeatherRepository
import com.example.weather.ui.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyWeatherViewModel @Inject constructor(
    private val repository: OpenWeatherRepository
) : ViewModel() {

    private var _state = MutableLiveData<State<DailyWeather>>()
    val state: LiveData<State<DailyWeather>> = _state

    fun getWeatherData(
        latitude: Double,
        longitude: Double,
    ) {
        _state.value = State.Loading()

        viewModelScope.launch {
            when (val result = repository.getDailyWeather(latitude, longitude)) {
                is NetworkResult.Success -> {
                    _state.postValue(State.Success(result.data!!))
                }

                is NetworkResult.Error -> {
                    _state.postValue(State.Error(result.message!!))
                }
            }
        }
    }
}