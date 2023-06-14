package com.example.weather.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather.common.Constants

/**
 * Represents a city entity in the local database
 */
@Entity(tableName = Constants.CITIES_TABLE_NAME)
data class CityEntity(
    @PrimaryKey val city: String,
    val latitude: Double,
    val longitude: Double
)