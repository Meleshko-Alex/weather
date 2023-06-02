package com.example.weather.ui.weather_daily

import ViewBindingKotlinModel
import com.airbnb.epoxy.EpoxyController
import com.example.weather.R
import com.example.weather.common.Utils
import com.example.weather.databinding.ItemDailyWeatherCardBinding
import com.example.weather.databinding.ItemHourlyWeatherCardBinding
import com.example.weather.domain.models.weather.DailyWeather
import com.example.weather.domain.models.weather.HourlyWeather
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DailyWeatherEpoxyController : EpoxyController() {

    var items: List<DailyWeather.OneDayWeather> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        items.forEach {
            ItemWeatherCardEpoxyModel(it).id(it.timeDate).addTo(this@DailyWeatherEpoxyController)
        }
    }

    data class ItemWeatherCardEpoxyModel(
        val weather: DailyWeather.OneDayWeather
    ) :
        ViewBindingKotlinModel<ItemDailyWeatherCardBinding>(R.layout.item_daily_weather_card) {

        override fun ItemDailyWeatherCardBinding.bind() {
            val date = Utils.convertEpochToLocalDate(weather.timeDate)
            tvDayName.text = date.substringBeforeLast(",") + ","
            tvDate.text = date.substringAfterLast(",")
            tvMaxTemp.text = weather.maxTemp.toString() + "°"
            tvMinTemp.text = " / " + weather.minTemp.toString() + "°"
            ivWeatherIcon.setImageResource(weather.weather.icon.iconNeutral)
        }
    }
}