package com.example.weather.common

import com.example.weather.R
import com.example.weather.domain.models.weather.WeatherType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UtilsTest {

    @Test
    fun getWeatherIcon_timeIs5_and_weatherTypeThunderstorm_returnsThunderstormNightIcon() {
        val weatherType = WeatherType.toWeatherType(221)
        val time = 0

        val expected = R.drawable.ic_scattered_thunderstorms_night
        val result = Utils.getWeatherIcon(weatherType, time)

        assertEquals(expected, result)
    }

    @Test
    fun getWeatherIcon_timeIs21_and_weatherTypeDrizzle_returnsDrizzleNightIcon() {
        val weatherType = WeatherType.toWeatherType(300)
        val time = 21

        val expected = R.drawable.ic_rainy_1_night
        val result = Utils.getWeatherIcon(weatherType, time)

        assertEquals(expected, result)
    }

    @Test
    fun getWeatherIcon_timeIs6_and_weatherTypeClear_returnsClearDayIcon() {
        val weatherType = WeatherType.toWeatherType(800)
        val time = 6

        val expected = R.drawable.ic_clear_day
        val result = Utils.getWeatherIcon(weatherType, time)

        assertEquals(expected, result)
    }

    @Test
    fun getWeatherIcon_timeIs20_and_weatherTypeClouds_returnsCloudsDayIcon() {
        val weatherType = WeatherType.toWeatherType(802)
        val time = 20

        val expected = R.drawable.ic_cloudy_2_day
        val result = Utils.getWeatherIcon(weatherType, time)

        assertEquals(expected, result)
    }
}