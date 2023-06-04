package com.example.weather.data.remote.mappers

import com.example.weather.data.remote.dto.CityDto
import com.example.weather.data.remote.dto.SearchCityResultDto
import com.example.weather.domain.models.cities.SearchCity
import com.example.weather.domain.models.cities.TopCities

fun CityDto.toCity(): TopCities.City {
    return TopCities.City(
        name = englishName,
        latitude = geoPosition.latitude,
        longitude = geoPosition.longitude
    )
}

fun SearchCityResultDto.toFoundCity(): SearchCity.FoundCity {
    return SearchCity.FoundCity(
        cityName = englishName,
        adminAreaName = adminArea.englishName,
        countryName = country.englishName,
        latitude = geoPosition.latitude,
        longitude = geoPosition.longitude
    )
}