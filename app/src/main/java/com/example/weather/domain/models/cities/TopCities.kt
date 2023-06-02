package com.example.weather.domain.models.cities

data class TopCities(
    val cities: List<City>
) {
    data class City(
        val name: String,
        val latitude: Double,
        val longitude: Double
    )
}
