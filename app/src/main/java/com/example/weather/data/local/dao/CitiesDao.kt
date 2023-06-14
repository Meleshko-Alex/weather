package com.example.weather.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.weather.common.Constants
import com.example.weather.data.local.entities.CityEntity

@Dao
interface CitiesDao {
    /**
     * Retrieves all cities from the database.
     * @return A list of [CityEntity] objects representing all the cities in the database.
     */
    @Query("SELECT * FROM ${Constants.CITIES_TABLE_NAME}")
    suspend fun getAll(): List<CityEntity>
}