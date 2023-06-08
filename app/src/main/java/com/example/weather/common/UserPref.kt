package com.example.weather.common

import com.example.weather.domain.models.cities.City

data class UserPref(
    val city: City,
    val measurementUnit: String
)
