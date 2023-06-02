package com.example.weather.di

import com.example.weather.data.remote.api.AccuWeatherService
import com.example.weather.data.remote.retrofit.OpenWeatherRetrofitClient
import com.example.weather.data.remote.api.OpenWeatherService
import com.example.weather.data.remote.retrofit.AccuWeatherRetrofitClient
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
    fun provideOpenWeatherService(): OpenWeatherService =
        OpenWeatherRetrofitClient.instance.create(OpenWeatherService::class.java)

    @Provides
    @Singleton
    fun provideAccuWeatherService(): AccuWeatherService =
        AccuWeatherRetrofitClient.instance.create(AccuWeatherService::class.java)
}