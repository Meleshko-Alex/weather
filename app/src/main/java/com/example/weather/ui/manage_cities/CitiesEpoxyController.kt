package com.example.weather.ui.manage_cities

import ViewBindingKotlinModel
import android.content.Context
import com.airbnb.epoxy.EpoxyController
import com.example.weather.R
import com.example.weather.databinding.ItemCityBinding
import com.example.weather.domain.models.cities.SearchCity
import com.example.weather.domain.models.cities.TopCities

class CitiesEpoxyController(
    private val context: Context,
    private val onItemClicked: (TopCities.City) -> Unit
) : EpoxyController() {

    var topCities: List<TopCities.City> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    var searchResultCities: List<SearchCity.FoundCity> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        if (searchResultCities.isEmpty()) {
            topCities.sortedBy {
                it.name
            }.forEach {
                ItemCityEpoxyModel(city = it, onItemClicked).id(it.name)
                    .addTo(this@CitiesEpoxyController)
            }
        }

        searchResultCities.forEach {
            ItemFoundCityEpoxyModel(
                city = it,
                context,
                onItemClicked
            ).id(it.cityName + it.adminAreaName + it.countryName)
                .addTo(this@CitiesEpoxyController)
        }
    }

    data class ItemCityEpoxyModel(
        val city: TopCities.City,
        val onItemClicked: (TopCities.City) -> Unit
    ) : ViewBindingKotlinModel<ItemCityBinding>(R.layout.item_city) {

        override fun ItemCityBinding.bind() {
            tvResult.text = city.name
            root.setOnClickListener {
                onItemClicked(city)
            }
        }
    }

    data class ItemFoundCityEpoxyModel(
        val city: SearchCity.FoundCity,
        val context: Context,
        val onItemClicked: (TopCities.City) -> Unit
    ) : ViewBindingKotlinModel<ItemCityBinding>(R.layout.item_city) {

        override fun ItemCityBinding.bind() {
            tvResult.text = context.resources.getString(
                R.string.found_city_format,
                city.cityName,
                city.adminAreaName,
                city.countryName
            )
            root.setOnClickListener {
                onItemClicked(
                    TopCities.City(
                        name = city.cityName,
                        latitude = city.latitude,
                        longitude = city.longitude
                    )
                )
            }
        }
    }
}