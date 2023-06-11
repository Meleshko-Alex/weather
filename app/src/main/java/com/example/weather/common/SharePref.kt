package com.example.weather.common

import android.content.Context
import android.content.pm.SharedLibraryInfo
import com.example.weather.domain.models.weather.WeatherToday
import com.google.gson.Gson

class SharedPref(context: Context) {
    private val sharedPref = context.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE)

    fun getWeatherToday(): WeatherToday {
        val weatherJson = sharedPref.getString(SharedPreferencesKeys.SHARED_PREF_TODAY_WEATHER, "")
        return Gson().fromJson(weatherJson, WeatherToday::class.java)
    }

    fun setWeatherToday(weather: WeatherToday) {
        val weatherJson = Gson().toJson(weather, WeatherToday::class.java)
        with(sharedPref.edit()) {
            putString(SharedPreferencesKeys.SHARED_PREF_TODAY_WEATHER, weatherJson)
            apply()
        }
    }

    fun getIsGuest(): Boolean {
        return sharedPref.getBoolean(SharedPreferencesKeys.SHARED_PREF_IS_GUEST, false)
    }

    fun setIsGuest(value: Boolean) {
        with(sharedPref.edit()) {
            putBoolean(SharedPreferencesKeys.SHARED_PREF_IS_GUEST, value)
            apply()
        }
    }
}