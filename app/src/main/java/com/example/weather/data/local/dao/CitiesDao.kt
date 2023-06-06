package com.example.weather.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.weather.common.Constants
import com.example.weather.data.local.entities.CityEntity
import com.example.weather.domain.models.cities.City

@Dao
interface CitiesDao {

    @Query("SELECT * FROM ${Constants.CITIES_TABLENAME}")
    suspend fun getAll(): List<CityEntity>
}