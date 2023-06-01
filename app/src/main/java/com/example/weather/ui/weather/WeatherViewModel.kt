package com.example.weather.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.common.Constants
import com.example.weather.data.network.NetworkResult
import com.example.weather.domain.models.weather.CurrentAndHourlyWeather
import com.example.weather.domain.models.weather.WeatherType
import com.example.weather.domain.repository.WeatherRepository
import com.example.weather.ui.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private var _state = MutableLiveData<State<CurrentAndHourlyWeather>>()
    val state: LiveData<State<CurrentAndHourlyWeather>> = _state

    fun getWeather(
        latitude: Double,
        longitude: Double,
    ) {
        _state.value = State.Loading()

        viewModelScope.launch {
            when (val result = repository.getCurrentAndHourlyWeather(latitude, longitude)) {
                is NetworkResult.Success -> {
                    _state.postValue(State.Success(result.data!!))
                }
                is NetworkResult.Error -> {
                    _state.postValue(State.Error(result.message!!))
                }
            }
        }
    }

    fun convertEpochToLocalTime(epochTime: Long): String {
        val instant =
            Instant.ofEpochMilli(epochTime * 1000) // Multiply by 1000 to convert to milliseconds
        val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("EEEE, d MMM")
        return localDateTime.format(formatter)
    }



}