package com.example.weather.presentation.weather_home

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.NotificationWorker
import com.example.weather.R
import com.example.weather.WeatherApplication
import com.example.weather.common.DataStoreManager
import com.example.weather.common.SharedPref
import com.example.weather.common.Utils
import com.example.weather.data.remote.NetworkResult
import com.example.weather.domain.models.weather.DailyWeather
import com.example.weather.domain.models.weather.HourlyWeather
import com.example.weather.domain.models.weather.OneDayWeather
import com.example.weather.domain.models.weather.WeatherToday
import com.example.weather.domain.repository.OpenWeatherRepository
import com.example.weather.domain.repository.WeatherDatabaseRepository
import com.example.weather.presentation.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.internal.wait
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeWeatherViewModel @Inject constructor(
    private val remoteRepository: OpenWeatherRepository,
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
                when (val result = remoteRepository.getWeather(latitude, longitude, measurementUnit)) {
                    is NetworkResult.Success -> {
                        isFetched = true
                        val hourlyWeather = HourlyWeather(
                            current = result.data!!.current,
                            hourly = result.data.hourly
                        )

                        // send data to UI
                        _state.postValue(State.Success(hourlyWeather))

                        // cache data
                        databaseRepository.saveHourlyWeatherData(hourlyWeather)
                        databaseRepository.saveDailyWeatherData(DailyWeather(result.data.daily))

                        // save weather data for the next day in Shared Preferences
                        saveTomorrowWeather(result.data.daily[0])
                    }

                    is NetworkResult.Error -> {
                        _state.postValue(State.Error(result.message!!))
                    }
                }
            }
        }
    }

    private fun getCachedData() {
        viewModelScope.launch {
            try {
                _state.postValue(State.Success(databaseRepository.getHourlyWeatherData()))
            } catch (e: Exception) {
                _state.postValue(State.Error(e.message ?: "Unknown error occurred during database call"))
            }

        }
    }

    private fun saveTomorrowWeather(weather: OneDayWeather) {
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

    fun fetchData() {
        isFetched = false
    }

    private fun scheduleWeatherNotification() {
        NotificationWorker.schedule(app)
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager =
            getApplication<WeatherApplication>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}