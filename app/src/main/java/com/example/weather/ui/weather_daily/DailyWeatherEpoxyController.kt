package com.example.weather.ui.weather_daily

import ViewBindingKotlinModel
import com.airbnb.epoxy.EpoxyController
import com.example.weather.R
import com.example.weather.common.Utils
import com.example.weather.databinding.ItemDailyWeatherBinding
import com.example.weather.domain.models.weather.DailyWeather
import com.example.weather.domain.models.weather.OneDayWeather

class DailyWeatherEpoxyController : EpoxyController() {

    var items: List<OneDayWeather> = emptyList()
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
        val weather: OneDayWeather
    ) :
        ViewBindingKotlinModel<ItemDailyWeatherBinding>(R.layout.item_daily_weather) {

        override fun ItemDailyWeatherBinding.bind() {
            val date = Utils.convertEpochToLocalDate(weather.timeDate)
            tvDayName.text = date.substringBeforeLast(",") + ","
            tvDate.text = date.substringAfterLast(",")
            tvMaxTemp.text = weather.maxTemp.toString() + "°"
            tvMinTemp.text = " / " + weather.minTemp.toString() + "°"
            ivWeatherIcon.setImageResource(weather.weather.icon.iconNeutral)
        }
    }
}