package com.example.weather.common

import com.example.weather.domain.models.cities.TopCities

data class UserPreferences(
    var currentCity: TopCities.City
)