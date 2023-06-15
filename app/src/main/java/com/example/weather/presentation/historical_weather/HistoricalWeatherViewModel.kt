package com.example.weather.presentation.historical_weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.common.DataStoreManager
import com.example.weather.common.Resource
import com.example.weather.domain.models.weather.HistoricalWeather
import com.example.weather.domain.repository.OpenWeatherRepository
import com.example.weather.presentation.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale
import java.util.TreeMap
import javax.inject.Inject

@HiltViewModel
class HistoricalWeatherViewModel @Inject constructor(
    private val remoteRepository: OpenWeatherRepository,
    dataStoreManager: DataStoreManager
) : ViewModel() {

    val userPref = dataStoreManager.getUserPref().asLiveData()

    private var _startDate = MutableStateFlow<Calendar>(Calendar.getInstance())
    val startDate: StateFlow<Calendar> = _startDate

    private var _endDate = MutableStateFlow<Calendar>(Calendar.getInstance())
    val endDate: StateFlow<Calendar> = _endDate

    private var _historicalWeatherState = MutableLiveData<State<List<HistoricalWeather>>>()
    val historicalWeatherState: LiveData<State<List<HistoricalWeather>>> = _historicalWeatherState

    private var _isEndDateTextInputLayoutEnabled = MutableStateFlow<Boolean>(false)
    val isEndDateTextInputLayoutEnabled: StateFlow<Boolean> = _isEndDateTextInputLayoutEnabled

    private var _isGenerateButtonEnabled = MutableStateFlow<Boolean>(false)
    val isGenerateButtonEnabled: StateFlow<Boolean> = _isGenerateButtonEnabled

    fun setStartDate(date: Calendar) {
        _startDate.value = date
    }

    fun setEndDate(date: Calendar) {
        _endDate.value = date
    }

    fun setIsEndDateTextInputLayoutEnabled(value: Boolean) {
        _isEndDateTextInputLayoutEnabled.value = value

    }

    fun setIsGenerateButtonEnabled(value: Boolean) {
        _isGenerateButtonEnabled.value = value
    }

    fun getDateString(date: Calendar): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return dateFormat.format(date.time)
    }

    fun getHistoricalDataRange(
        latitude: Double,
        longitude: Double,
        units: String
    ) {
        _historicalWeatherState.value = State.Loading()

        viewModelScope.launch {

            when (val resource = remoteRepository.getHistoricalWeatherRange(latitude, longitude, units,  startDate.value.clone() as Calendar, endDate.value)) {
                is Resource.Success -> {
                    _historicalWeatherState.postValue(State.Success(resource.data!!))
                }

                is Resource.Error -> {
                    _historicalWeatherState.postValue(State.Error(resource.message!!))
                }
            }
        }
    }

    fun getAverageTemp(data: List<HistoricalWeather>): Int {
        var sum = 0
        data.forEach {
            sum += it.temp
        }
        return sum / data.size
    }
}