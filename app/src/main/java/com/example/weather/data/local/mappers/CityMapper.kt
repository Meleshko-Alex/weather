package com.example.weather.data.local.mappers

import com.example.weather.data.local.entities.CityEntity
import com.example.weather.data.remote.dto.CityDto
import com.example.weather.domain.models.cities.TopCities

fun CityEntity.toCity(): TopCities.City {
    return TopCities.City(
        name = city,
        latitude = latitude,
        longitude = longitude
    )
}