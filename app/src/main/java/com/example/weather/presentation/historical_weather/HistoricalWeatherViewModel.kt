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

    private fun getNumberOfDays(): Int {
        val d1 = with(startDate.value!!) {
            LocalDate.of(
                this.get(Calendar.YEAR),
                this.get(Calendar.MONTH),
                this.get(Calendar.DAY_OF_MONTH)
            )
        }
        val d2 = with(endDate.value!!) {
            LocalDate.of(
                this.get(Calendar.YEAR),
                this.get(Calendar.MONTH),
                this.get(Calendar.DAY_OF_MONTH)
            )
        }

        return ChronoUnit.DAYS.between(d1, d2).toInt()
    }

    fun getHistoricalData(
        latitude: Double,
        longitude: Double,
        units: String
    ) {
        val map = TreeMap<Int, HistoricalWeather>()
        val startDate = startDate.value.clone() as Calendar
        var i = 0

        _historicalWeatherState.value = State.Loading()

        viewModelScope.launch {
            val job = viewModelScope.launch(Dispatchers.IO) {
                while(startDate <= endDate.value) {
                    when (val result = remoteRepository.getHistoricalWeather(latitude, longitude, units, startDate.timeInMillis / 1000)) {
                        is Resource.Success -> {
                            map[i] = result.data!!
                        }

                        is Resource.Error -> {
                            Log.e(this@HistoricalWeatherViewModel.javaClass.simpleName, result.message!!)
                        }
                    }
                    i++
                    startDate.add(Calendar.DATE, 1)
                }
            }
            job.join()
            _historicalWeatherState.postValue(State.Success(map.values.toList()))
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