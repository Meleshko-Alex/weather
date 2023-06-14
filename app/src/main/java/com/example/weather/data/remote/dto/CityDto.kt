package com.example.weather.data.remote.dto

import com.squareup.moshi.Json

/**
 * Represent the DTO for the City data received from the API.
 */
data class CityDto(
    @Json(name = "Version") val version: Int = 0,
    @Json(name = "Key") val key: String = "",
    @Json(name = "Type") val type: String = "",
    @Json(name = "Rank") val rank: Int = 0,
    @Json(name = "LocalizedName") val localizedName: String = "",
    @Json(name = "EnglishName") val englishName: String = "",
    @Json(name = "PrimaryPostalCode") val primaryPostalCode: String = "",
    @Json(name = "GeoPosition") val geoPosition: GeoPositionDto = GeoPositionDto()
) {
    /**
     * Represent the DTO for the geographic position of a city.
     */
    data class GeoPositionDto(
        @Json(name = "Latitude") val latitude: Double = 0.0,
        @Json(name = "Longitude") val longitude: Double = 0.0,
    )
}
