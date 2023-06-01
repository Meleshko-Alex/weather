package com.example.weather.ui.weather

import ViewBindingKotlinModel
import com.airbnb.epoxy.EpoxyController
import com.example.weather.R
import com.example.weather.common.Utils
import com.example.weather.databinding.ItemWeatherCardBinding
import com.example.weather.domain.models.weather.CurrentAndHourlyWeather
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class WeatherHourlyEpoxyController : EpoxyController() {

    var items: List<CurrentAndHourlyWeather.CurrentWeather> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        items.forEach {
            ItemWeatherCardEpoxyModel(it).id(it.time).addTo(this@WeatherHourlyEpoxyController)
        }
    }

    data class ItemWeatherCardEpoxyModel(
        val weather: CurrentAndHourlyWeather.CurrentWeather
    ) :
        ViewBindingKotlinModel<ItemWeatherCardBinding>(R.layout.item_weather_card) {

        override fun ItemWeatherCardBinding.bind() {
            val time = convertEpochToLocalTime(weather.time)
            tvTime.text = time
            tvTemperature.text = weather.temp.toInt().toString()
            ivWeatherIcon.setImageResource(
                Utils.getWeatherIcon(
                    weather = weather.weather,
                    time = time.substringBeforeLast(":").toInt()
                )
            )
        }

        private fun convertEpochToLocalTime(epochTime: Long): String {
            val instant = Instant.ofEpochSecond(epochTime)
            val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            return localDateTime.format(formatter)
        }
    }
}