package com.example.weather.presentation.weather_home

import com.example.weather.domain.models.weather.HourlyWeather
import com.example.weather.domain.models.weather.OneHourWeather
import com.robinhood.spark.SparkAdapter

class MySparkAdapter(private val hourlyWeather: List<OneHourWeather>) : SparkAdapter() {

    override fun getCount(): Int {
        return hourlyWeather.size
    }

    override fun getItem(index: Int): Any {
        return hourlyWeather[index].temp.toFloat()
    }

    override fun getY(index: Int): Float {
        return hourlyWeather[index].temp.toFloat()
    }
}