package com.example.weather.ui.manage_cities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.common.DataStoreManager
import com.example.weather.domain.models.cities.City
import com.example.weather.data.remote.NetworkResult
import com.example.weather.domain.models.cities.SearchCity
import com.example.weather.domain.models.cities.TopCities
import com.example.weather.domain.repository.AccuWeatherRepository
import com.example.weather.domain.repository.WeatherDatabaseRepository
import com.example.weather.ui.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageCitiesViewModel @Inject constructor(
    private val networkRepository: AccuWeatherRepository,
    private val databaseRepository: WeatherDatabaseRepository,
    private val dataStoreManager: DataStoreManager
): ViewModel() {
    private var _citiesState = MutableLiveData<State<TopCities>>()
    val citiesState: LiveData<State<TopCities>> = _citiesState
    private var _searchCityState = MutableLiveData<State<SearchCity>>()
    val searchCityState: LiveData<State<SearchCity>> = _searchCityState
    var searchQuery: String? = null
    val currentCity = dataStoreManager.getCurrentCity().asLiveData()

    fun getTopCities() {
        viewModelScope.launch {
            _citiesState.postValue(State.Success(databaseRepository.getTopCities()))
        }
    }

    fun searchCity(query: String) {
        _searchCityState.value = State.Loading()

        viewModelScope.launch {
            when (val result = networkRepository.searchCity(query)) {
                is NetworkResult.Success -> {
                    _searchCityState.postValue( State.Success(result.data!!))
                }

                is NetworkResult.Error -> {
                    _searchCityState.postValue(State.Error(result.message!!))
                }
            }
        }
    }

    fun setCurrentCity(city: City) {
        viewModelScope.launch {
            dataStoreManager.setCurrentCity(city)
        }
    }
}