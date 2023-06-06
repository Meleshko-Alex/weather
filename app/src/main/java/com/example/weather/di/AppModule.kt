package com.example.weather.di

import android.content.Context
import com.example.weather.common.DataStoreManager
import com.example.weather.data.local.dao.CitiesDao
import com.example.weather.data.local.dao.HourlyWeatherDao
import com.example.weather.data.local.database.WeatherDatabase
import com.example.weather.data.remote.api.AccuWeatherService
import com.example.weather.data.remote.retrofit.OpenWeatherRetrofitClient
import com.example.weather.data.remote.api.OpenWeatherService
import com.example.weather.data.remote.retrofit.AccuWeatherRetrofitClient
import com.example.weather.domain.repository.WeatherDatabaseRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideOpenWeatherService(): OpenWeatherService =
        OpenWeatherRetrofitClient.instance.create(OpenWeatherService::class.java)

    @Provides
    @Singleton
    fun provideAccuWeatherService(): AccuWeatherService =
        AccuWeatherRetrofitClient.instance.create(AccuWeatherService::class.java)

    @Provides
    fun provideCitiesDao(database: WeatherDatabase): CitiesDao {
        return database.citiesDao()
    }

    @Provides
    fun provideHourlyWeatherDao(database: WeatherDatabase): HourlyWeatherDao {
        return database.hourlyWeatherDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return WeatherDatabase.createDatabase(context)
    }


    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager =
        DataStoreManager(context)
}