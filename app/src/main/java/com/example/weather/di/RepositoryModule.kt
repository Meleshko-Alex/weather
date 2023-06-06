package com.example.weather.di

import com.example.weather.data.repository.AccuWeatherRepositoryImpl
import com.example.weather.data.repository.OpenWeatherRepositoryImpl
import com.example.weather.data.repository.WeatherDatabaseRepositoryImpl
import com.example.weather.domain.repository.AccuWeatherRepository
import com.example.weather.domain.repository.OpenWeatherRepository
import com.example.weather.domain.repository.WeatherDatabaseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindOpenWeatherRepository(
        openWeatherRepositoryImpl: OpenWeatherRepositoryImpl
    ): OpenWeatherRepository

    @Binds
    @Singleton
    abstract fun bindAccuWeatherRepository(
        accuWeatherRepositoryImpl: AccuWeatherRepositoryImpl
    ): AccuWeatherRepository

    @Binds
    @Singleton
    abstract fun bindWeatherDatabaseRepository(
        weatherDatabaseRepositoryImpl: WeatherDatabaseRepositoryImpl
    ): WeatherDatabaseRepository
}