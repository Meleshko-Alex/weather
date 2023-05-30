package com.example.weather.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.network.NetworkResult
import com.example.weather.domain.models.WeatherData
import com.example.weather.domain.repository.WeatherRepository
import com.example.weather.ui.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private var _state = MutableLiveData<State<WeatherData>>()
    val state: LiveData<State<WeatherData>> = _state

    fun getWeather(
        latitude: Double,
        longitude: Double,
    ) {
        _state.value = State.Loading()

        viewModelScope.launch {
            when (val result = repository.getCurrentWeather(latitude, longitude)) {
                is NetworkResult.Success -> {
                    _state.value = State.Success(WeatherData(result.data!!))
                }
                is NetworkResult.Error -> {
                    _state.value = State.Error(result.message!!)
                }
            }
        }
    }

}