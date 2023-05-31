package com.example.weather.ui.weather

import ViewBindingKotlinModel
import android.content.Context
import android.graphics.ColorFilter
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyController
import com.example.weather.R
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
            tvTime.text = convertEpochToLocalTime(weather.time)
            tvTemperature.text = weather.temp.toInt().toString()
            ivWeatherIcon.setImageResource(weather.weather.icon.iconSmall)
        }

        private fun convertEpochToLocalTime(epochTime: Long): String {
            val instant = Instant.ofEpochSecond(epochTime)
            val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            return localDateTime.format(formatter)
        }
    }
}