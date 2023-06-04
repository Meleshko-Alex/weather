package com.example.weather.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey val city: String,
    val latitude: Double,
    val longitude: Double
)