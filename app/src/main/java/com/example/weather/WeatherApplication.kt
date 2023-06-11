package com.example.weather

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import com.example.weather.common.Constants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApplication : Application()