package com.example.weather.presentation.temperature_graph

import android.accounts.NetworkErrorException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.common.DataStoreManager
import com.example.weather.data.remote.NetworkResult
import com.example.weather.domain.models.weather.HistoricalWeather
import com.example.weather.domain.repository.OpenWeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale
import java.util.TreeMap
import javax.inject.Inject

@HiltViewModel
class TempGraphViewModel @Inject constructor(
    private val remoteRepository: OpenWeatherRepository,
    dataStoreManager: DataStoreManager
) : ViewModel() {

    private var _startDate = MutableLiveData<Calendar>()
    val startDate: LiveData<Calendar> = _startDate
    private var _finishDate = MutableLiveData<Calendar>()
    val finishDate: LiveData<Calendar> = _finishDate
    val userPref = dataStoreManager.getUserPref().asLiveData()
    val a = MutableLiveData<String>()

    fun setStartDate(date: Calendar) {
        _startDate.value = date
    }

    fun setFinishDate(date: Calendar) {
        _finishDate.value = date
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
        val d2 = with(finishDate.value!!) {
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
        if (startDate.value == null || finishDate.value == null) {
            return
        }

        val map = TreeMap<Int, HistoricalWeather>()
        val startDate = startDate.value!!
        var i = 0

        viewModelScope.launch {
            val job = viewModelScope.launch(Dispatchers.IO) {
                while(startDate <= finishDate.value!!) {
                    when (val result = remoteRepository.getHistoricalWeather(latitude, longitude, units, startDate.timeInMillis / 1000)) {
                        is NetworkResult.Success -> {
                            map[i] = result.data!!
                        }

                        is NetworkResult.Error -> {
                            Log.e(this@TempGraphViewModel.javaClass.simpleName, result.message!!)
                        }
                    }
                    i++
                    startDate.add(Calendar.DATE, 1)
                }
            }
            job.join()
            a.postValue(map.toString())
        }



    }
}