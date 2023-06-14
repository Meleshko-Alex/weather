package com.example.weather.data.remote.retrofit

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Singleton class for creating and configuring the Retrofit client for AccuWeather API.
 */
class AccuWeatherClient {
    companion object {
        // get an instance of the RetrofitClient
        val instance: Retrofit by lazy {
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val okHttp = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(okHttp)
                .build()
        }

        private const val BASE_URL = "https://dataservice.accuweather.com/"
    }
}