package com.example.weather.domain.models.weather

data class CurrentAndHourlyWeather(
    val current: CurrentWeather,
    val hourly: List<CurrentWeather>
) {
    data class CurrentWeather(
        val time: Long,
        val date: String,
        val temp: Double,
        val feelsLikeTemperature: Double,
        val humidity: Int,
        val uvi: Double,
        val windSpeed: Double,
        val weather: WeatherType
    )

//    data class HourlyWeather(
//        val forecastedDataTime: Int,
//        val temp: Double,
//        val feelsLikeTemperature: Double,
//        val humidity: Int,
//        val uvi: Double,
//        val windSpeed: Double,
//        val weather: Weather
//    )

    data class Weather(
        val icon: String = "",
        val main: String = ""
    )
}