package com.example.weather.domain.models.weather

import androidx.annotation.DrawableRes
import com.example.weather.R

sealed class WeatherType(
    val weather: WeatherName,
    val icon: Icon
) {
    data class Icon(
        @DrawableRes val iconSmallDay: Int = 0,
        @DrawableRes val iconNormalDay: Int = 0,
        @DrawableRes val iconSmallNight: Int = 0,
        @DrawableRes val iconNormalNight: Int = 0
    )

    object LightThunderstorm : WeatherType(
        weather = WeatherName.LIGHT_THUNDERSTORM,
        icon = Icon(
            iconSmallDay = R.drawable.ic_isolated_thunderstorms_day_small,
            iconNormalDay = R.drawable.ic_isolated_thunderstorms_day_normal,
            iconSmallNight = R.drawable.ic_isolated_thunderstorms_night_small,
            iconNormalNight = R.drawable.ic_isolated_thunderstorms_night_normal   
        )
    )

    object HeavyThunderstorm : WeatherType(
        weather = WeatherName.HEAVY_THUNDERSTORM,
        icon = Icon(
            iconSmallDay = R.drawable.ic_severe_thunderstorm_small,
            iconNormalDay = R.drawable.ic_severe_thunderstorm_normal,
            iconSmallNight = R.drawable.ic_severe_thunderstorm_small,
            iconNormalNight = R.drawable.ic_severe_thunderstorm_normal
        )
    )

    object Thunderstorm : WeatherType(
        weather = WeatherName.THUNDERSTORM,
        icon = Icon(
            iconSmallDay = R.drawable.ic_scattered_thunderstorms_day_small,
            iconNormalDay = R.drawable.ic_scattered_thunderstorms_day_normal,
            iconSmallNight = R.drawable.ic_scattered_thunderstorms_night_small,
            iconNormalNight = R.drawable.ic_scattered_thunderstorms_night_normal
        )
    )

    object Drizzle : WeatherType(
        weather = WeatherName.DRIZZLE,
        icon = Icon(
            iconSmallDay = R.drawable.ic_rainy_1_small,
            iconNormalDay = R.drawable.ic_rainy_1_normal,
            iconSmallNight = R.drawable.ic_rainy_1_small,
            iconNormalNight = R.drawable.ic_rainy_1_normal
        )
    )

    object LightRain : WeatherType(
        weather = WeatherName.LIGHT_RAIN,
        icon = Icon(
            iconSmallDay = R.drawable.ic_rainy_1_day_small,
            iconNormalDay = R.drawable.ic_rainy_1_day_normal,
            iconSmallNight = R.drawable.ic_rainy_1_night_small,
            iconNormalNight = R.drawable.ic_rainy_1_night_normal
        )
    )

    object Rain : WeatherType(
        weather = WeatherName.RAIN,
        icon = Icon(
            iconSmallDay = R.drawable.ic_rainy_2_day_small,
            iconNormalDay = R.drawable.ic_rainy_2_day_normal,
            iconSmallNight = R.drawable.ic_rainy_2_night_small,
            iconNormalNight = R.drawable.ic_rainy_2_night_normal
        )
    )

    object HeavyRain : WeatherType(
        weather = WeatherName.HEAVY_RAIN,
        icon = Icon(
            iconSmallDay = R.drawable.ic_rainy_3_day_small,
            iconNormalDay = R.drawable.ic_rainy_3_day_normal,
            iconSmallNight = R.drawable.ic_rainy_3_night_small,
            iconNormalNight = R.drawable.ic_rainy_3_night_normal
        )
    )

    object Showers : WeatherType(
        weather = WeatherName.SHOWERS,
        icon = Icon(
            iconSmallDay = R.drawable.ic_rainy_3_small,
            iconNormalDay = R.drawable.ic_rainy_3_normal,
            iconSmallNight = R.drawable.ic_rainy_3_small,
            iconNormalNight = R.drawable.ic_rainy_3_normal
        )
    )

    object LightSnow : WeatherType(
        weather = WeatherName.LIGHT_SNOW,
        icon = Icon(
            iconSmallDay = R.drawable.ic_snowy_1_day_small,
            iconNormalDay = R.drawable.ic_snowy_1_day_normal,
            iconSmallNight = R.drawable.ic_snowy_1_night_small,
            iconNormalNight = R.drawable.ic_snowy_1_night_normal
        )
    )

    object Snow : WeatherType(
        weather = WeatherName.SNOW,
        icon = Icon(
            iconSmallDay = R.drawable.ic_snowy_2_day_small,
            iconNormalDay = R.drawable.ic_snowy_2_day_normal,
            iconSmallNight = R.drawable.ic_snowy_2_night_small,
            iconNormalNight = R.drawable.ic_snowy_2_night_normal
        )
    )

    object RainSnow : WeatherType(
        weather = WeatherName.RAIN_SNOW,
        icon = Icon(
            iconSmallDay = R.drawable.ic_rain_and_snow_small,
            iconNormalDay = R.drawable.ic_rain_and_snow_normal,
            iconSmallNight = R.drawable.ic_rain_and_snow_small,
            iconNormalNight = R.drawable.ic_rain_and_snow_normal,
        )
    )

    object Sleet : WeatherType(
        weather = WeatherName.SLEET,
        icon = Icon(
            iconSmallDay = R.drawable.ic_snow_and_sleet_small,
            iconNormalDay = R.drawable.ic_snow_and_sleet_normal,
            iconSmallNight = R.drawable.ic_snow_and_sleet_small,
            iconNormalNight = R.drawable.ic_snow_and_sleet_normal,
        )
    )

    object RainSleet : WeatherType(
        weather = WeatherName.RAIN_SLEET,
        icon = Icon(
            iconSmallDay = R.drawable.ic_rain_and_sleet_small,
            iconNormalDay = R.drawable.ic_rain_and_sleet_normal,
            iconSmallNight = R.drawable.ic_rain_and_sleet_small,
            iconNormalNight = R.drawable.ic_rain_and_sleet_normal,
        )
    )

    object HeavySnow : WeatherType(
        weather = WeatherName.HEAVY_SNOW,
        icon = Icon(
            iconSmallDay = R.drawable.ic_snowy_3_day_small,
            iconNormalDay = R.drawable.ic_snowy_3_day_normal,
            iconSmallNight = R.drawable.ic_snowy_3_night_small,
            iconNormalNight = R.drawable.ic_snowy_3_night_normal
        )
    )

    object Mist : WeatherType(
        weather = WeatherName.MIST,
        icon = Icon(
            iconSmallDay = R.drawable.ic_fog_day_small,
            iconNormalDay = R.drawable.ic_fog_day_normal,
            iconSmallNight = R.drawable.ic_fog_night_small,
            iconNormalNight = R.drawable.ic_fog_night_normal
        )
    )

    object Smoke : WeatherType(
        weather = WeatherName.SMOKE,
        icon = Icon(
            iconSmallDay = R.drawable.ic_fog_day_small,
            iconNormalDay = R.drawable.ic_fog_day_normal,
            iconSmallNight = R.drawable.ic_fog_night_small,
            iconNormalNight = R.drawable.ic_fog_night_normal
        )
    )

    object Haze : WeatherType(
        weather = WeatherName.HAZE,
        icon = Icon(
            iconSmallDay = R.drawable.ic_haze_day_small,
            iconNormalDay = R.drawable.ic_haze_day_normal,
            iconSmallNight = R.drawable.ic_haze_night_small,
            iconNormalNight = R.drawable.ic_haze_night_normal
        )
    )

    object Dust : WeatherType(
        weather = WeatherName.DUST,
        icon = Icon(
            iconSmallDay = R.drawable.ic_dust_small,
            iconNormalDay = R.drawable.ic_dust_normal,
            iconSmallNight = R.drawable.ic_dust_small,
            iconNormalNight = R.drawable.ic_dust_normal
        )
    )

    object Fog : WeatherType(
        weather = WeatherName.FOG,
        icon = Icon(
            iconSmallDay = R.drawable.ic_fog_day_small,
            iconNormalDay = R.drawable.ic_fog_day_normal,
            iconSmallNight = R.drawable.ic_fog_night_small,
            iconNormalNight = R.drawable.ic_fog_night_normal
        )
    )

    object Squall : WeatherType(
        weather = WeatherName.SQUALL,
        icon = Icon(
            iconSmallDay = R.drawable.ic_wind_small,
            iconNormalDay = R.drawable.ic_wind_normall,
            iconSmallNight = R.drawable.ic_wind_small,
            iconNormalNight = R.drawable.ic_wind_normall
        )
    )

    object Tornado : WeatherType(
        weather = WeatherName.TORNADO,
        icon = Icon(
            iconSmallDay = R.drawable.ic_tornado_small,
            iconNormalDay = R.drawable.ic_tornado_normal,
            iconSmallNight = R.drawable.ic_tornado_small,
            iconNormalNight = R.drawable.ic_tornado_normal
        )
    )

    object Clear : WeatherType(
        weather = WeatherName.CLEAR,
        icon = Icon(
            iconSmallDay = R.drawable.ic_clear_day_small,
            iconNormalDay = R.drawable.ic_clear_day_normal,
            iconSmallNight = R.drawable.ic_clear_night_small,
            iconNormalNight = R.drawable.ic_clear_night_normal
        )
    )

    object Clouds : WeatherType(
        weather = WeatherName.CLOUDS,
        icon = Icon(
            iconSmallDay = R.drawable.ic_cloudy_2_day_small,
            iconNormalDay = R.drawable.ic_cloudy_2_day_normal,
            iconSmallNight = R.drawable.ic_cloudy_2_night_small,
            iconNormalNight = R.drawable.ic_cloudy_2_night_normal
        )
    )

    object FewClouds : WeatherType(
        weather = WeatherName.FEW_CLOUDS,
        icon = Icon(
            iconSmallDay = R.drawable.ic_cloudy_1_day_small,
            iconNormalDay = R.drawable.ic_cloudy_1_day_normal,
            iconSmallNight = R.drawable.ic_cloudy_1_night_small,
            iconNormalNight = R.drawable.ic_cloudy_1_night_normal
        )
    )

    object OvercastClouds : WeatherType(
        weather = WeatherName.OVERCAST_CLOUDS,
        icon = Icon(
            iconSmallDay = R.drawable.ic_cloudy_3_day_small,
            iconNormalDay = R.drawable.ic_cloudy_3_day_normal,
            iconSmallNight = R.drawable.ic_cloudy_3_night_small,
            iconNormalNight = R.drawable.ic_cloudy_3_night_normal
        )
    )

    companion object {
        fun toWeatherType(id: Int): WeatherType {
            return when (id) {
                200 -> LightThunderstorm
                201 -> Thunderstorm
                202 -> HeavyThunderstorm
                210 -> LightThunderstorm
                211 -> Thunderstorm
                212 -> HeavyThunderstorm
                221 -> Thunderstorm
                230 -> LightThunderstorm
                231 -> Thunderstorm
                232 -> HeavyThunderstorm
                300 -> Drizzle
                301 -> Drizzle
                302 -> Drizzle
                310 -> Drizzle
                311 -> Drizzle
                312 -> Drizzle
                313 -> Drizzle
                314 -> Drizzle
                321 -> Drizzle
                500 -> LightRain
                501 -> Rain
                502 -> HeavyRain
                503 -> HeavyRain
                504 -> HeavyRain
                511 -> HeavyRain
                520 -> Showers
                521 -> Showers
                522 -> Showers
                531 -> Showers
                600 -> LightSnow
                601 -> Snow
                602 -> HeavySnow
                611 -> Sleet
                612 -> RainSleet
                613 -> RainSleet
                615 -> RainSnow
                616 -> RainSnow
                620 -> RainSnow
                621 -> RainSnow
                622 -> RainSnow
                701 -> Mist
                711 -> Smoke
                721 -> Haze
                731 -> Dust
                741 -> Fog
                751 -> Dust
                761 -> Dust
                762 -> Dust
                771 -> Squall
                781 -> Tornado
                800 -> Clear
                801 -> FewClouds
                802 -> Clouds
                803 -> OvercastClouds
                804 -> OvercastClouds
                else -> Clear
            }
        }

        enum class WeatherName(val weatherName: String) {
            LIGHT_THUNDERSTORM("Light thunderstorm"),
            HEAVY_THUNDERSTORM("Heavy thunderstorm"),
            THUNDERSTORM("Thunderstorm"),
            DRIZZLE("Drizzle"),
            LIGHT_RAIN("Light rain"),
            RAIN("Rain"),
            HEAVY_RAIN("Heavy rain"),
            SHOWERS("Showers"),
            LIGHT_SNOW("Light snow"),
            SNOW("Snow"),
            RAIN_SNOW("Rain and snow"),
            SLEET("Sleet"),
            RAIN_SLEET("Rain and sleet"),
            HEAVY_SNOW("Heavy snow"),
            MIST("Mist"),
            FOG("Fog"),
            SMOKE("Smoke"),
            HAZE("Haze"),
            DUST("Dust"),
            SQUALL("Squall"),
            TORNADO("Tornado"),
            CLEAR("Clear sky"),
            CLOUDS("Clouds"),
            FEW_CLOUDS("Few clouds"),
            OVERCAST_CLOUDS("Overcast clouds"),
        }
    }
}