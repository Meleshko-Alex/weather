package com.example.weather.di

import android.content.Context
import com.example.weather.common.DataStoreManager
import com.example.weather.data.local.EntityMapper
import com.example.weather.data.local.dao.CitiesDao
import com.example.weather.data.local.dao.DailyWeatherDao
import com.example.weather.data.local.dao.HourlyWeatherDao
import com.example.weather.data.local.database.WeatherDatabase
import com.example.weather.data.remote.DtoMapper
import com.example.weather.data.remote.api.AccuWeatherService
import com.example.weather.data.remote.api.OpenWeatherService
import com.example.weather.data.remote.retrofit.AccuWeatherClient
import com.example.weather.data.remote.retrofit.OpenWeatherRetrofitClient
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
        AccuWeatherClient.instance.create(AccuWeatherService::class.java)

    @Provides
    fun provideCitiesDao(database: WeatherDatabase): CitiesDao {
        return database.citiesDao()
    }

    @Provides
    fun provideHourlyWeatherDao(database: WeatherDatabase): HourlyWeatherDao {
        return database.hourlyWeatherDao()
    }

    @Provides
    fun provideDailyWeatherDao(database: WeatherDatabase): DailyWeatherDao {
        return database.dailyWeatherDao()
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

    @Provides
    @Singleton
    fun provideDtoMapper(): DtoMapper {
        return DtoMapper()
    }

    @Provides
    @Singleton
    fun provideEntityMapper(): EntityMapper {
        return EntityMapper()
    }
}