package com.example.weather.data.remote.retrofit

import com.example.weather.data.remote.api.AccuWeatherService
import com.example.weather.data.remote.api.OpenWeatherService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AccuWeatherRetrofitClient {
    companion object {
        val instance: Retrofit by lazy {
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val okHttp = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
            Retrofit.Builder()
                .baseUrl(AccuWeatherService.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(okHttp)
                .build()
        }
    }
}