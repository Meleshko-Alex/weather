package com.example.weather.presentation.manage_cities

import ViewBindingKotlinModel
import android.content.Context
import com.airbnb.epoxy.EpoxyController
import com.example.weather.R
import com.example.weather.domain.models.cities.City
import com.example.weather.databinding.ItemCityBinding
import com.example.weather.domain.models.cities.SearchCity

class CitiesEpoxyController(
    private val context: Context,
    private val onItemClicked: (City) -> Unit
) : EpoxyController() {

    var topCities: List<City> = emptyList()
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
        val city: City,
        val onItemClicked: (City) -> Unit
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
        val onItemClicked: (City) -> Unit
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
                    City(
                        name = city.cityName,
                        latitude = city.latitude,
                        longitude = city.longitude
                    )
                )
            }
        }
    }
}