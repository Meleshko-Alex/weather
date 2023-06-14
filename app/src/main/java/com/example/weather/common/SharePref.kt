package com.example.weather.common

import android.content.Context
import com.example.weather.domain.models.weather.WeatherTomorrow
import com.google.gson.Gson

/**
 * Manages shared preferences for storing weather-related data and user information.
 *
 * @param context the application context.
 */
class SharedPref(context: Context) {
    private val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    /**
     * Retrieves the weather information for today from shared preferences.
     *
     * @return the weather information for today.
     */
    fun getTomorrowWeather(): WeatherTomorrow {
        val weatherJson = sharedPref.getString(SharedPreferencesKeys.SHARED_PREF_WEATHER_TOMORROW, "")
        return Gson().fromJson(weatherJson, WeatherTomorrow::class.java)
    }

    /**
     * Sets the weather information for today in shared preferences.
     *
     * @param weather the weather information for today.
     */
    fun setTomorrowWeather(weather: WeatherTomorrow) {
        val weatherJson = Gson().toJson(weather, WeatherTomorrow::class.java)
        with(sharedPref.edit()) {
            putString(SharedPreferencesKeys.SHARED_PREF_WEATHER_TOMORROW, weatherJson)
            apply()
        }
    }

    /**
     * Retrieves the user's guest status from shared preferences.
     *
     * @return `true` if the user is a guest, `false` otherwise.
     */
    fun getIsGuest(): Boolean {
        return sharedPref.getBoolean(SharedPreferencesKeys.SHARED_PREF_IS_GUEST, false)
    }

    /**
     * Sets the user's guest status in shared preferences.
     *
     * @param value the guest status to set.
     */
    fun setIsGuest(value: Boolean) {
        with(sharedPref.edit()) {
            putBoolean(SharedPreferencesKeys.SHARED_PREF_IS_GUEST, value)
            apply()
        }
    }

    companion object {
        private const val SHARED_PREFERENCES_NAME = "weather_shared_pref"
    }
}