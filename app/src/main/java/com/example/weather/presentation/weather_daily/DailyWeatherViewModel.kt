package com.example.weather.presentation.weather_daily

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.common.DataStoreManager
import com.example.weather.domain.models.weather.DailyWeather
import com.example.weather.domain.repository.WeatherDatabaseRepository
import com.example.weather.presentation.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyWeatherViewModel @Inject constructor(
    private val databaseRepository: WeatherDatabaseRepository,
    dataStoreManager: DataStoreManager,
) : ViewModel() {

    private var _state = MutableLiveData<State<DailyWeather>>()
    val state: LiveData<State<DailyWeather>> = _state
    val userPref = dataStoreManager.getUserPref().asLiveData()

    fun getDailyWeatherData() {
        _state.value = State.Loading()

        // get cached data
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(State.Success(databaseRepository.getDailyWeatherData()))
        }
    }
}