package com.example.weather.ui.weather_home

import ViewBindingKotlinModel
import android.content.Context
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyController
import com.example.weather.R
import com.example.weather.common.Utils
import com.example.weather.databinding.ItemHourlyWeatherBinding
import com.example.weather.domain.models.weather.HourlyWeather
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class HourlyWeatherEpoxyController(
    private val onItemClicked: (HourlyWeather.CurrentWeather) -> Unit,
    private val context: Context
) : EpoxyController() {

    var items: List<HourlyWeather.CurrentWeather> = emptyList()
        set(value) {
            field = value
            selectedItem = items[0]
            requestModelBuild()
        }

    var selectedItem: HourlyWeather.CurrentWeather? = null
        set(value) {
            field = value
            requestModelBuild()
        }


    override fun buildModels() {
        items.forEach {
            ItemWeatherCardEpoxyModel(weather = it, onItemClicked, context, selectedItem).id(it.timeDate).addTo(this@HourlyWeatherEpoxyController)

        }
    }

    data class ItemWeatherCardEpoxyModel(
        val weather: HourlyWeather.CurrentWeather,
        val onItemClicked: (HourlyWeather.CurrentWeather) -> Unit,
        val context: Context,
        val selectedItem: HourlyWeather.CurrentWeather?
    ) : ViewBindingKotlinModel<ItemHourlyWeatherBinding>(R.layout.item_hourly_weather) {

        override fun ItemHourlyWeatherBinding.bind() {
            val time = convertEpochToLocalTime(weather.timeDate)
            tvTime.text = time
            tvTemperature.text = weather.temp.toString()
            ivWeatherIcon.setImageResource(
                Utils.getWeatherIcon(
                    weather = weather.weather,
                    time = time.substringBeforeLast(":").toInt()
                )
            )

            if (weather == selectedItem) {
                root.strokeWidth = 0
                root.setCardBackgroundColor(ContextCompat.getColor(context, R.color.blue))
                tvTime.setTextColor(ContextCompat.getColor(context, R.color.white))
                tvTemperature.setTextColor(ContextCompat.getColor(context, R.color.white))
            } else {root.strokeWidth = 3
                root.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
                tvTime.setTextColor(ContextCompat.getColor(context, R.color.gray))
                tvTemperature.setTextColor(ContextCompat.getColor(context, R.color.black))
            }

            root.setOnClickListener {
                onItemClicked(weather)
            }
        }

        private fun convertEpochToLocalTime(epochTime: Long): String {
            val instant = Instant.ofEpochSecond(epochTime)
            val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            return localDateTime.format(formatter)
        }
    }
}