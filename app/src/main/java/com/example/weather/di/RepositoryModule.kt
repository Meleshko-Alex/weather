package com.example.weather.di

import com.example.weather.data.remote.repository.AccuWeatherRepositoryImpl
import com.example.weather.data.remote.repository.OpenWeatherRepositoryImpl
import com.example.weather.domain.repository.AccuWeatherRepository
import com.example.weather.domain.repository.OpenWeatherRepository
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
}