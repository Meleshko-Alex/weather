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
import com.example.weather.R
import com.example.weather.WeatherApplication
import com.example.weather.common.DataStoreManager
import com.example.weather.common.Resource
import com.example.weather.common.SharedPref
import com.example.weather.common.Utils
import com.example.weather.domain.models.weather.DailyWeather
import com.example.weather.domain.models.weather.HourlyWeather
import com.example.weather.domain.models.weather.OneDayWeather
import com.example.weather.domain.models.weather.WeatherTomorrow
import com.example.weather.domain.notifications.NotificationWorker
import com.example.weather.domain.repository.OpenWeatherRepository
import com.example.weather.domain.repository.WeatherDatabaseRepository
import com.example.weather.presentation.main.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeWeatherViewModel @Inject constructor(
    private val remoteRepository: OpenWeatherRepository,
    private val databaseRepository: WeatherDatabaseRepository,
    private val app: Application,
    dataStoreManager: DataStoreManager
) : AndroidViewModel(app) {

    private var _weatherState = MutableLiveData<State<HourlyWeather>>()
    val weatherState: LiveData<State<HourlyWeather>> = _weatherState
    val userPref = dataStoreManager.getUserPref().asLiveData()
    private var isFetched = false

    init {
        scheduleWeatherNotification()
    }

    /**
     * Retrieves weather data for the specified location.
     *
     * @param latitude the latitude of the location.
     * @param longitude the longitude of the location.
     * @param measurementUnit the unit of measurement for the weather data.
     */
    fun getWeatherData(
        latitude: Double,
        longitude: Double,
        measurementUnit: String
    ) {
        // get cached data from db if no Internet connection detected, make an api call otherwise
        if (!hasInternetConnection() || isFetched) {
            if (SharedPref(app).getIsFirstLaunch()) {
                return
            }
            getCachedData()
        } else {
            viewModelScope.launch {
                _weatherState.value = State.Loading()

                when (val result = remoteRepository.getWeather(latitude, longitude, measurementUnit)) {
                    is Resource.Success -> {
                        isFetched = true
                        val hourlyWeather = HourlyWeather(
                            current = result.data!!.current,
                            hourly = result.data.hourly
                        )

                        // send data to UI
                        _weatherState.postValue(State.Success(hourlyWeather))

                        // cache data
                        databaseRepository.saveHourlyWeatherData(hourlyWeather)
                        databaseRepository.saveDailyWeatherData(DailyWeather(result.data.daily))

                        // save weather data for the next day in Shared Preferences
                        saveTomorrowWeather(result.data.daily[0])

                        // check that the app has already been launched and fetched data
                        SharedPref(app).setIsFirstLaunch(false)
                    }

                    is Resource.Error -> {
                        _weatherState.postValue(State.Error(result.message!!))
                    }
                }
            }
        }
    }

    private fun getCachedData() {
        viewModelScope.launch {
            _weatherState.value = State.Loading()

            when (val resource = databaseRepository.getHourlyWeatherData()) {
                is Resource.Success -> {
                    _weatherState.postValue(State.Success(resource.data!!))
                }

                is Resource.Error -> {
                    _weatherState.postValue(State.Error(resource.message!!))
                }
            }
        }
    }

    private fun saveTomorrowWeather(weather: OneDayWeather) {
        with(weather) {
            val weatherToday = WeatherTomorrow(
                date = Utils.convertEpochToLocalDate(this.timeDate, java.time.format.DateTimeFormatter.ofPattern("MM.dd.yyyy")),
                summary = app.applicationContext.getString(
                    R.string.notification_text,
                    this.maxTemp,
                    this.minTemp,
                    this.summary
                )
            )
            SharedPref(app).setTomorrowWeather(weatherToday)
        }
    }

    /**
     * Sets the flag to fetch data from network to `true`
     */
    fun fetchData() {
        isFetched = false
    }

    /**
     * Starts [notification worker][NotificationWorker] that periodically sends notifications to a user
     */
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