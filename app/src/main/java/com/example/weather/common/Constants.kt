package com.example.weather.common

import com.example.weather.domain.models.cities.TopCities

object Constants {
    const val NIGHT_TIME = 21 // constant represents 21:00 time and is used for choosing a day/night icon
    const val DAY_TIME = 6 // constant represent 06:00 time and is used for choosing a day/night icon
    const val DATASTORE_PREFERENCES_NAME = "settings"
    val DEFAULT_CITY = TopCities.City("London", 51.5072, -0.1275)
}