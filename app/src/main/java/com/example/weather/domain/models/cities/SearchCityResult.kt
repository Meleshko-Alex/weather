package com.example.weather.domain.models.cities

data class SearchCityResult(
    val foundCities: List<FoundCity>
) {
    data class FoundCity(
        val cityName: String,
        val adminAreaName: String,
        val countryName: String,
        val latitude: Double,
        val longitude: Double
    )
}