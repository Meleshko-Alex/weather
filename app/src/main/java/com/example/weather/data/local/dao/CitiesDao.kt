package com.example.weather.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.weather.data.local.entities.CityEntity

@Dao
interface CitiesDao {

    @Query("SELECT * FROM cities")
    suspend fun getAll(): List<CityEntity>
}