package com.example.weather.data.remote.dto

import com.squareup.moshi.Json

/**
 * Represents the DTO for a city search result data received from the API
 */
data class SearchCityResultDto(
    @Json(name = "Version") val version: Int = 0,
    @Json(name = "Key") val key: String = "",
    @Json(name = "Type") val type: String = "",
    @Json(name = "Rank") val rank: Int = 0,
    @Json(name = "LocalizedName") val localizedName: String = "",
    @Json(name = "EnglishName") val englishName: String = "",
    @Json(name = "PrimaryPostalCode") val primaryPostalCode: String = "",
    @Json(name = "Country") val country: CountryDto = CountryDto(),
    @Json(name = "AdministrativeArea") val adminArea: AdministrativeAreaDto = AdministrativeAreaDto(),
    @Json(name = "GeoPosition") val geoPosition: GeoPositionDto = GeoPositionDto()
) {
    /**
     * Represents the DTO for the country data.
     */
    data class CountryDto(
        @Json(name = "ID") val id: String = "",
        @Json(name = "LocalizedName") val localizedName: String = "",
        @Json(name = "EnglishName") val englishName: String = "",
    )

    /**
     * Represents the DTO for the administrative area data.
     */
    data class AdministrativeAreaDto(
        @Json(name = "ID") val id: String = "",
        @Json(name = "LocalizedName") val localizedName: String = "",
        @Json(name = "EnglishName") val englishName: String = "",
        @Json(name = "Level") val level: Int = 0,
        @Json(name = "LocalizedType") val localizedType: String = "",
        @Json(name = "EnglishType") val englishType: String = "",
        @Json(name = "CountryID") val countryId: String = ""
    )

    /**
     * Represents the DTO for the geoposition data.
     */
    data class GeoPositionDto(
        @Json(name = "Latitude") val latitude: Double = 0.0,
        @Json(name = "Longitude") val longitude: Double = 0.0,
    )
}