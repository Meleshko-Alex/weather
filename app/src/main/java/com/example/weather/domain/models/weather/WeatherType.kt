package com.example.weather.domain.models.weather

import androidx.annotation.DrawableRes
import com.example.weather.R

sealed class WeatherType(
    val weather: WEATHER_NAME,
    val icon: Icon
) {
    data class Icon(
        @DrawableRes val iconSmall: Int,
        @DrawableRes val iconNormal: Int
    )

    object ThunderstormLightRain : WeatherType(
        weather = WEATHER_NAME.THUNDERSTORM_LIGHT_RAIN,
        icon = Icon(
            iconSmall = R.drawable.ic_storm_48,
            iconNormal = R.drawable.ic_storm_96
        )
    )

    object ThunderstormHeavyRain : WeatherType(
        weather = WEATHER_NAME.THUNDERSTORM_HEAVY_RAIN,
        icon = Icon(
            iconSmall = R.drawable.ic_storm_with_heavy_rain_48,
            iconNormal = R.drawable.ic_storm_with_heavy_rain_96
        )
    )

    object Thunderstorm : WeatherType(
        weather = WEATHER_NAME.THUNDERSTORM,
        icon = Icon(
            iconSmall = R.drawable.ic_cloud_lightning_48,
            iconNormal = R.drawable.ic_cloud_lightning_96
        )
    )

    object Drizzle : WeatherType(
        weather = WEATHER_NAME.DRIZZLE,
        icon = Icon(
            iconSmall = R.drawable.ic_rain_cloud_48,
            iconNormal = R.drawable.ic_rain_cloud_96
        )
    )

    object LightRain : WeatherType(
        weather = WEATHER_NAME.LIGHT_RAIN,
        icon = Icon(
            iconSmall = R.drawable.ic_light_rain_48,
            iconNormal = R.drawable.ic_light_rain_96
        )
    )

    object Rain : WeatherType(
        weather = WEATHER_NAME.RAIN,
        icon = Icon(
            iconSmall = R.drawable.ic_rain_48,
            iconNormal = R.drawable.ic_rain_96
        )
    )

    object HeavyRain : WeatherType(
        weather = WEATHER_NAME.HEAVY_RAIN,
        icon = Icon(
            iconSmall = R.drawable.ic_heavy_rain_48,
            iconNormal = R.drawable.ic_heavy_rain_96
        )
    )

    object Showers : WeatherType(
        weather = WEATHER_NAME.SHOWERS,
        icon = Icon(
            iconSmall = R.drawable.ic_rainfall_48,
            iconNormal = R.drawable.ic_rainfall_96
        )
    )

    object Snow : WeatherType(
        weather = WEATHER_NAME.SNOW,
        icon = Icon(
            iconSmall = R.drawable.ic_snow_48,
            iconNormal = R.drawable.ic_snow_96,
        )
    )

    object Sleet : WeatherType(
        weather = WEATHER_NAME.SLEET,
        icon = Icon(
            iconSmall = R.drawable.ic_sleet_48,
            iconNormal = R.drawable.ic_sleet_96
        )
    )

    object HeavySnow : WeatherType(
        weather = WEATHER_NAME.HEAVY_SNOW,
        icon = Icon(
            iconSmall = R.drawable.ic_snow_storm_48,
            iconNormal = R.drawable.ic_snow_storm_96
        )
    )

    object Mist : WeatherType(
        weather = WEATHER_NAME.MIST,
        icon = Icon(
            iconSmall = R.drawable.ic_fog_48,
            iconNormal = R.drawable.ic_fog_96
        )
    )

    object Smoke : WeatherType(
        weather = WEATHER_NAME.SMOKE,
        icon = Icon(
            iconSmall = R.drawable.ic_fog_48,
            iconNormal = R.drawable.ic_fog_96
        )
    )

    object Haze : WeatherType(
        weather = WEATHER_NAME.HAZE,
        icon = Icon(
            iconSmall = R.drawable.ic_haze_48,
            iconNormal = R.drawable.ic_haze_96
        )
    )

    object Dust : WeatherType(
        weather = WEATHER_NAME.DUST,
        icon = Icon(
            iconSmall = R.drawable.ic_dust_48,
            iconNormal = R.drawable.ic_dust_96
        )
    )

    object Fog : WeatherType(
        weather = WEATHER_NAME.FOG,
        icon = Icon(
            iconSmall = R.drawable.ic_fog_48,
            iconNormal = R.drawable.ic_fog_96
        )
    )

    object Squall : WeatherType(
        weather = WEATHER_NAME.SQUALL,
        icon = Icon(
            iconSmall = R.drawable.ic_wind_48,
            iconNormal = R.drawable.ic_wind_96
        )
    )

    object Tornado : WeatherType(
        weather = WEATHER_NAME.TORNADO,
        icon = Icon(
            iconSmall = R.drawable.ic_tornado_48,
            iconNormal = R.drawable.ic_tornado_96
        )
    )

    object Clear : WeatherType(
        weather = WEATHER_NAME.CLEAR,
        icon = Icon(
            iconSmall = R.drawable.ic_sun_48,
            iconNormal = R.drawable.ic_sun_96
        )
    )

    object Clouds : WeatherType(
        weather = WEATHER_NAME.CLOUDS,
        icon = Icon(
            iconSmall = R.drawable.ic_cloud_48,
            iconNormal = R.drawable.ic_cloud_96
        )
    )

    object FewClouds : WeatherType(
        weather = WEATHER_NAME.FEW_CLOUDS,
        icon = Icon(
            iconSmall = R.drawable.ic_partly_cloudy_day_48,
            iconNormal = R.drawable.ic_partly_cloudy_day_96
        )
    )

    object OvercastClouds : WeatherType(
        weather = WEATHER_NAME.OVERCAST_CLOUDS,
        icon = Icon(
            iconSmall = R.drawable.ic_clouds_48,
            iconNormal = R.drawable.ic_clouds_96
        )
    )

    companion object {
        fun toWeatherType(id: Int): WeatherType {
            return when (id) {
                200 -> ThunderstormLightRain
                201 -> ThunderstormLightRain
                202 -> ThunderstormHeavyRain
                210 -> Thunderstorm
                211 -> Thunderstorm
                212 -> Thunderstorm
                221 -> Thunderstorm
                230 -> ThunderstormLightRain
                231 -> ThunderstormLightRain
                232 -> ThunderstormHeavyRain
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
                600 -> Snow
                601 -> Snow
                602 -> HeavySnow
                611 -> Sleet
                612 -> Sleet
                613 -> Snow
                615 -> Snow
                616 -> Snow
                620 -> Snow
                621 -> Snow
                622 -> Snow
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

        enum class WEATHER_NAME(name: String) {
            THUNDERSTORM_LIGHT_RAIN("Thunderstorm with light rain"),
            THUNDERSTORM_HEAVY_RAIN("Thunderstorm with heavy rain"),
            THUNDERSTORM("Thunderstorm"),
            DRIZZLE("Drizzle"),
            LIGHT_RAIN("Light rain"),
            RAIN("Rain"),
            HEAVY_RAIN("Heavy rain"),
            SHOWERS("Showers"),
            SNOW("Snow"),
            SLEET("Sleet"),
            HEAVY_SNOW("Heavy snow"),
            MIST("Mist"),
            FOG("Fog"),
            SMOKE("Smoke"),
            HAZE("Haze"),
            DUST("Dust"),
            Sand("Sand"),
            SQUALL("Squall"),
            TORNADO("Tornado"),
            CLEAR("Clear sky"),
            CLOUDS("Clouds"),
            FEW_CLOUDS("Few clouds"),
            OVERCAST_CLOUDS("Overcast clouds")
        }
    }
}