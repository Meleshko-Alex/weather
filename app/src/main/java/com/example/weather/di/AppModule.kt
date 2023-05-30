package com.example.weather.di

import com.example.weather.data.network.RetrofitClient
import com.example.weather.data.network.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideWeatherService(): WeatherService =
        RetrofitClient.instance.create(WeatherService::class.java)
}