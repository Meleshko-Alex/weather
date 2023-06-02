package com.example.weather.data.remote.mappers

import com.example.weather.data.remote.dto.CityDto
import com.example.weather.domain.models.cities.TopCities

fun CityDto.toCity(): TopCities.City {
    return TopCities.City(
        name = englishName,
        latitude = geoPosition.latitude,
        longitude = geoPosition.longitude
    )
}