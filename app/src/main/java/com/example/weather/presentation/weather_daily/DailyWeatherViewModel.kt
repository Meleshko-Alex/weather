package com.example.weather.presentation.weather_daily

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.common.DataStoreManager
import com.example.weather.common.hasInternetConnection
import com.example.weather.data.remote.NetworkResult
import com.example.weather.domain.models.weather.DailyWeather
import com.example.weather.domain.repository.OpenWeatherRepository
import com.example.weather.domain.repository.WeatherDatabaseRepository
import com.example.weather.presentation.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyWeatherViewModel @Inject constructor(
    private val repository: OpenWeatherRepository,
    private val databaseRepository: WeatherDatabaseRepository,
    dataStoreManager: DataStoreManager,
    app: Application
) : AndroidViewModel(app) {

    private var _state = MutableLiveData<State<DailyWeather>>()
    val state: LiveData<State<DailyWeather>> = _state
    val userPref = dataStoreManager.getUserPref().asLiveData()
    private var isFetched = true // true by default since we get data in HomeWeatherViewModel

    fun getWeatherData(
        latitude: Double,
        longitude: Double,
        measurementUnit: String
    ) {
        _state.value = State.Loading()

        // get cached data from db if not Internet connection detected, make an api call otherwise
        if (!hasInternetConnection() || isFetched) {
            getCachedData()
        } else {
            getDailyWeatherData(latitude, longitude, measurementUnit)
        }
    }

    private fun getCachedData() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(State.Success(databaseRepository.getDailyWeatherData()))
        }
    }

    private fun getDailyWeatherData(
        latitude: Double,
        longitude: Double,
        measurementUnit: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getDailyWeather(latitude, longitude, measurementUnit)) {
                is NetworkResult.Success -> {
                    _state.postValue(State.Success(result.data!!))
                    databaseRepository.saveDailyWeatherData(result.data)
                    isFetched = true
                }

                is NetworkResult.Error -> {
                    _state.postValue(State.Error(result.message!!))
                }
            }
        }
    }

    fun fetchData() {
        isFetched = false
    }
}