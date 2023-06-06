package com.example.weather.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather.common.Constants

@Entity(tableName = Constants.CITIES_TABLENAME)
data class CityEntity(
    @PrimaryKey val city: String,
    val latitude: Double,
    val longitude: Double
)