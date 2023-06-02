package com.example.weather.ui.cities_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.remote.NetworkResult
import com.example.weather.domain.models.cities.TopCities
import com.example.weather.domain.repository.AccuWeatherRepository
import com.example.weather.ui.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesListViewModel @Inject constructor(
    private val repository: AccuWeatherRepository
): ViewModel() {

    private var _state = MutableLiveData<State<TopCities>>()
    val state: LiveData<State<TopCities>> = _state

    fun getTopCities() {
        _state.value = State.Loading()

        viewModelScope.launch {
            when (val result = repository.getTopCities()) {
                is NetworkResult.Success -> {
                    _state.value = State.Success(result.data!!)
                }

                is NetworkResult.Error -> {
                    _state.value = State.Error(result.message!!)
                }
            }
        }
    }
}