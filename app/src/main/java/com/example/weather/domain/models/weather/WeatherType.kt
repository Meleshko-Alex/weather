package com.example.weather.domain.models.weather

import androidx.annotation.DrawableRes
import com.example.weather.R

/**
 * Represents different types of weather conditions, along with their corresponding icons.
 * @property weather the weather associated with the weather type.
 * @property icon the Icon object containing the drawable resource IDs for the weather type's icons.
 */
sealed class WeatherType(
    val weather: WeatherName,
    val icon: Icon
) {
    data class Icon(
        @DrawableRes val iconDay: Int,
        @DrawableRes val iconNight: Int,
        @DrawableRes val iconNeutral: Int = 0
    )

    object LightThunderstorm : WeatherType(
        weather = WeatherName.LIGHT_THUNDERSTORM,
        icon = Icon(
            iconDay = R.drawable.ic_isolated_thunderstorms_day,
            iconNight = R.drawable.ic_isolated_thunderstorms_night,
            iconNeutral = R.drawable.ic_isolated_thunderstorms
        )
    )

    object HeavyThunderstorm : WeatherType(
        weather = WeatherName.HEAVY_THUNDERSTORM,
        icon = Icon(
            iconDay = R.drawable.ic_severe_thunderstorm,
            iconNight = R.drawable.ic_severe_thunderstorm,
            iconNeutral = R.drawable.ic_severe_thunderstorm
        )
    )

    object Thunderstorm : WeatherType(
        weather = WeatherName.THUNDERSTORM,
        icon = Icon(
            iconDay = R.drawable.ic_scattered_thunderstorms_day,
            iconNight = R.drawable.ic_scattered_thunderstorms_night,
            iconNeutral = R.drawable.ic_scattered_thunderstorms
        )
    )

    object Drizzle : WeatherType(
        weather = WeatherName.DRIZZLE,
        icon = Icon(
            iconDay = R.drawable.ic_rainy_1_day,
            iconNight = R.drawable.ic_rainy_1_night,
            iconNeutral = R.drawable.ic_rainy_1
        )
    )

    object LightRain : WeatherType(
        weather = WeatherName.LIGHT_RAIN,
        icon = Icon(
            iconDay = R.drawable.ic_rainy_1_day,
            iconNight = R.drawable.ic_rainy_1_night,
            iconNeutral = R.drawable.ic_rainy_1
        )
    )

    object Rain : WeatherType(
        weather = WeatherName.RAIN,
        icon = Icon(
            iconDay = R.drawable.ic_rainy_2_day,
            iconNight = R.drawable.ic_rainy_2_night,
            iconNeutral = R.drawable.ic_rainy_2
        )
    )

    object HeavyRain : WeatherType(
        weather = WeatherName.HEAVY_RAIN,
        icon = Icon(
            iconDay = R.drawable.ic_rainy_3_day,
            iconNight = R.drawable.ic_rainy_3_night,
            iconNeutral = R.drawable.ic_rainy_3
        )
    )

    object Showers : WeatherType(
        weather = WeatherName.SHOWERS,
        icon = Icon(
            iconDay = R.drawable.ic_rainy_3,
            iconNight = R.drawable.ic_rainy_3,
            iconNeutral = R.drawable.ic_rainy_3
        )
    )

    object LightSnow : WeatherType(
        weather = WeatherName.LIGHT_SNOW,
        icon = Icon(
            iconDay = R.drawable.ic_snowy_1_day,
            iconNight = R.drawable.ic_snowy_1_night,
            iconNeutral = R.drawable.ic_snowy_1
        )
    )

    object Snow : WeatherType(
        weather = WeatherName.SNOW,
        icon = Icon(
            iconDay = R.drawable.ic_snowy_2_day,
            iconNight = R.drawable.ic_snowy_2_night,
            iconNeutral = R.drawable.ic_snowy_2
        )
    )

    object RainSnow : WeatherType(
        weather = WeatherName.RAIN_SNOW,
        icon = Icon(
            iconDay = R.drawable.ic_rain_and_snow,
            iconNight = R.drawable.ic_rain_and_snow,
            iconNeutral = R.drawable.ic_rain_and_snow
        )
    )

    object Sleet : WeatherType(
        weather = WeatherName.SLEET,
        icon = Icon(
            iconDay = R.drawable.ic_snow_and_sleet,
            iconNight = R.drawable.ic_snow_and_sleet,
            iconNeutral = R.drawable.ic_snow_and_sleet
        )
    )

    object RainSleet : WeatherType(
        weather = WeatherName.RAIN_SLEET,
        icon = Icon(
            iconDay = R.drawable.ic_rain_and_sleet,
            iconNight = R.drawable.ic_rain_and_sleet,
            iconNeutral = R.drawable.ic_rain_and_sleet,
        )
    )

    object HeavySnow : WeatherType(
        weather = WeatherName.HEAVY_SNOW,
        icon = Icon(
            iconDay = R.drawable.ic_snowy_3_day,
            iconNight = R.drawable.ic_snowy_3_night,
            iconNeutral = R.drawable.ic_snowy_3
        )
    )

    object Mist : WeatherType(
        weather = WeatherName.MIST,
        icon = Icon(
            iconDay = R.drawable.ic_fog_day,
            iconNight = R.drawable.ic_fog_night,
            iconNeutral = R.drawable.ic_fog
        )
    )

    object Smoke : WeatherType(
        weather = WeatherName.SMOKE,
        icon = Icon(
            iconDay = R.drawable.ic_fog_day,
            iconNight = R.drawable.ic_fog_night,
            iconNeutral = R.drawable.ic_fog
        )
    )

    object Haze : WeatherType(
        weather = WeatherName.HAZE,
        icon = Icon(
            iconDay = R.drawable.ic_haze_day,
            iconNight = R.drawable.ic_haze_night,
            iconNeutral = R.drawable.ic_haze
        )
    )

    object Dust : WeatherType(
        weather = WeatherName.DUST,
        icon = Icon(
            iconDay = R.drawable.ic_dust,
            iconNight = R.drawable.ic_dust,
            iconNeutral = R.drawable.ic_dust
        )
    )

    object Fog : WeatherType(
        weather = WeatherName.FOG,
        icon = Icon(
            iconDay = R.drawable.ic_fog_day,
            iconNight = R.drawable.ic_fog_night,
            iconNeutral = R.drawable.ic_fog
        )
    )

    object Squall : WeatherType(
        weather = WeatherName.SQUALL,
        icon = Icon(
            iconDay = R.drawable.ic_wind,
            iconNight = R.drawable.ic_wind,
            iconNeutral = R.drawable.ic_wind
        )
    )

    object Tornado : WeatherType(
        weather = WeatherName.TORNADO,
        icon = Icon(
            iconDay = R.drawable.ic_tornado,
            iconNight = R.drawable.ic_tornado,
            iconNeutral = R.drawable.ic_tornado
        )
    )

    object Clear : WeatherType(
        weather = WeatherName.CLEAR,
        icon = Icon(
            iconDay = R.drawable.ic_clear_day,
            iconNight = R.drawable.ic_clear_night,
            iconNeutral = R.drawable.ic_clear_day
        )
    )

    object Clouds : WeatherType(
        weather = WeatherName.CLOUDS,
        icon = Icon(
            iconDay = R.drawable.ic_cloudy_2_day,
            iconNight = R.drawable.ic_cloudy_2_night,
            iconNeutral = R.drawable.ic_cloudy
        )
    )

    object FewClouds : WeatherType(
        weather = WeatherName.FEW_CLOUDS,
        icon = Icon(
            iconDay = R.drawable.ic_cloudy_1_day,
            iconNight = R.drawable.ic_cloudy_1_night,
            iconNeutral = R.drawable.ic_cloudy
        )
    )

    object OvercastClouds : WeatherType(
        weather = WeatherName.OVERCAST_CLOUDS,
        icon = Icon(
            iconDay = R.drawable.ic_cloudy_3_day,
            iconNight = R.drawable.ic_cloudy_3_night,
            iconNeutral = R.drawable.ic_cloudy
        )
    )

    companion object {

        /**
         * Mapps the given weather ID to the corresponding WeatherType.
         * @param id the weather ID
         * @return the WeatherType corresponding to the provided weather ID.
         */
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