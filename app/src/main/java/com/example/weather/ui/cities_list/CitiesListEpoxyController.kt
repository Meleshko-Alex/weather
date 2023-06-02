package com.example.weather.ui.cities_list

import ViewBindingKotlinModel
import com.airbnb.epoxy.EpoxyController
import com.example.weather.R
import com.example.weather.databinding.ItemSearchResultBinding
import com.example.weather.domain.models.cities.TopCities

class CitiesListEpoxyController : EpoxyController() {

    var cities: List<TopCities.City> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        cities.sortedBy {
            it.name
        }.forEach {
            ItemSearchResultEpoxyModel(it).id(it.name).addTo(this@CitiesListEpoxyController)
        }
    }

    data class ItemSearchResultEpoxyModel(
        val city: TopCities.City
    ) : ViewBindingKotlinModel<ItemSearchResultBinding>(R.layout.item_search_result) {

        override fun ItemSearchResultBinding.bind() {
            tvResult.text = city.name
        }
    }
}