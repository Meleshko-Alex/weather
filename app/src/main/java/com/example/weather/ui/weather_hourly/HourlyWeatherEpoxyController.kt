package com.example.weather.ui.weather_hourly

import ViewBindingKotlinModel
import android.content.Context
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyController
import com.example.weather.R
import com.example.weather.common.Utils
import com.example.weather.databinding.ItemHourlyWeatherCardBinding
import com.example.weather.domain.models.weather.HourlyWeather
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class HourlyWeatherEpoxyController : EpoxyController() {

    var items: List<HourlyWeather.CurrentWeather> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        items.forEach {
            ItemWeatherCardEpoxyModel(weather = it).id(it.timeDate).addTo(this@HourlyWeatherEpoxyController)
        }
    }

    data class ItemWeatherCardEpoxyModel(
        val weather: HourlyWeather.CurrentWeather,
    ) : ViewBindingKotlinModel<ItemHourlyWeatherCardBinding>(R.layout.item_hourly_weather_card) {

        override fun ItemHourlyWeatherCardBinding.bind() {
            val time = convertEpochToLocalTime(weather.timeDate)
            tvTime.text = time
            tvTemperature.text = weather.temp.toString()
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