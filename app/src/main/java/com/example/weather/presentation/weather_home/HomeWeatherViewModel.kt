package com.example.weather.presentation.weather_home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.NotificationWorker
import com.example.weather.R
import com.example.weather.common.DataStoreManager
import com.example.weather.common.SharedPref
import com.example.weather.common.Utils
import com.example.weather.common.hasInternetConnection
import com.example.weather.data.remote.NetworkResult
import com.example.weather.domain.models.weather.HourlyWeather
import com.example.weather.domain.models.weather.OneDayWeather
import com.example.weather.domain.models.weather.WeatherToday
import com.example.weather.domain.repository.OpenWeatherRepository
import com.example.weather.domain.repository.WeatherDatabaseRepository
import com.example.weather.presentation.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.math.log
import kotlin.time.measureTimedValue

@HiltViewModel
class HomeWeatherViewModel @Inject constructor(
    private val repository: OpenWeatherRepository,
    private val databaseRepository: WeatherDatabaseRepository,
    private val app: Application,
    dataStoreManager: DataStoreManager
) : AndroidViewModel(app) {
    private var _state = MutableLiveData<State<HourlyWeather>>()
    val state: LiveData<State<HourlyWeather>> = _state
    val userPref = dataStoreManager.getUserPref().asLiveData()
    private var isFetched = false

    init {
        scheduleWeatherNotification()
    }

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
            viewModelScope.launch {
                // get hourly weather
                getHourlyWeather(latitude, longitude, measurementUnit)

                // get and cache daily weather
                getDailyWeather(latitude, longitude, measurementUnit)
            }
        }
    }

    private fun getCachedData() {
        viewModelScope.launch {
            _state.postValue(State.Success(databaseRepository.getHourlyWeatherData()))
        }
    }

    private suspend fun getHourlyWeather(
        latitude: Double,
        longitude: Double,
        measurementUnit: String
    ) {
        when (val result = repository.getHourlyWeather(latitude, longitude, measurementUnit)) {
            is NetworkResult.Success -> {
                _state.postValue(State.Success(result.data!!))
                // save data to db
                databaseRepository.saveHourlyWeatherData(result.data)
                isFetched = true
            }

            is NetworkResult.Error -> {
                _state.postValue(State.Error(result.message!!))
            }
        }
    }

    private suspend fun getDailyWeather(
        latitude: Double,
        longitude: Double,
        measurementUnit: String
    ) {
        fun saveTomorrowWeather(weather: OneDayWeather) {
            with(weather) {
                val weatherToday = WeatherToday(
                    date = Utils.convertEpochToLocalDate(this.timeDate, java.time.format.DateTimeFormatter.ofPattern("MM.dd.yyyy")),
                    summary = app.applicationContext.getString(
                        R.string.notification_text,
                        this.maxTemp,
                        this.minTemp,
                        this.summary
                    )
                )
                SharedPref(app).setWeatherToday(weatherToday)
            }
        }

        val result = repository.getDailyWeather(latitude, longitude, measurementUnit)
        if (result is NetworkResult.Success) {
            // save tomorrows weather in shared pref for use in notification
            saveTomorrowWeather(result.data!!.daily[0])
            databaseRepository.saveDailyWeatherData(result.data)
        }
    }

    fun fetchData() {
        isFetched = false
    }

    private fun scheduleWeatherNotification() {
        NotificationWorker.schedule(app)
    }
}