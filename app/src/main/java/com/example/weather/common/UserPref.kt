package com.example.weather.common

import com.example.weather.domain.models.cities.City

/**
 * Represents the user's preferences
 */
data class UserPref(
    val city: City,
    val measurementUnit: String
)
