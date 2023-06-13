package com.example.weather.presentation.temperature_graph

import com.example.weather.domain.models.weather.HistoricalWeather
import com.example.weather.domain.models.weather.HourlyWeather
import com.example.weather.domain.models.weather.OneHourWeather
import com.robinhood.spark.SparkAdapter

class MySparkAdapter(private val historicalWeather: List<HistoricalWeather>) : SparkAdapter() {

    override fun getCount(): Int {
        return historicalWeather.size
    }

    override fun getItem(index: Int): Any {
        return "${historicalWeather[index].temp}\n${historicalWeather[index].date}"
    }

    override fun getY(index: Int): Float {
        return historicalWeather[index].temp.toFloat()
    }
}