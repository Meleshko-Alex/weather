package com.example.weather.presentation.historical_weather

import com.example.weather.domain.models.weather.HistoricalWeather
import com.robinhood.spark.SparkAdapter

class MySparkAdapter(private val historicalWeather: List<HistoricalWeather>) : SparkAdapter() {

    override fun getCount(): Int {
        return historicalWeather.size
    }

    override fun getItem(index: Int): Any {
        return "${historicalWeather[index].date} - ${historicalWeather[index].temp}"
    }

    override fun getY(index: Int): Float {
        return historicalWeather[index].temp.toFloat()
    }
}