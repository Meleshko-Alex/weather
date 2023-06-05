package com.example.weather.ui.map

import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ActionMode
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.weather.R
import com.example.weather.databinding.FragmentMapBinding
import com.example.weather.domain.models.cities.TopCities
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback, OnMapClickListener {
    private var _binding: FragmentMapBinding? = null
    private val binding: FragmentMapBinding get() = _binding!!
    private val args: MapFragmentArgs by navArgs()
    private lateinit var currentCity: TopCities.City
    private lateinit var map: GoogleMap
    private val viewModel: MapViewModel by viewModels()
    private var selectedCity: TopCities.City? = null
    private var actionMode: ActionMode? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentCity = Gson().fromJson(args.currentCity, TopCities.City::class.java)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val position = LatLng(currentCity.latitude, currentCity.longitude)
        val markedPosition = googleMap.addMarker(
            MarkerOptions()
                .position(position)
                .title("Marker")

        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 10f))
        googleMap.setOnMapClickListener(this)
//        markedPosition?.showInfoWindow()
    }

    override fun onMapClick(positionClicked: LatLng) {
        checkReadyThen {
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(positionClicked, 10f)
            val city = getPositionClickedCity(positionClicked)
            city?.let {
                Log.d(this.javaClass.simpleName, "$it")
                selectedCity = it
                val marker = map.addMarker(
                    MarkerOptions()
                        .position(positionClicked)
                        .title(it.name)
                )
                marker?.showInfoWindow()
                map.moveCamera(cameraUpdate)

                if (actionMode == null) {
                    actionMode = requireActivity().startActionMode(actionModelCallback)
                }
            }
        }
    }


    private fun getPositionClickedCity(positionClicked: LatLng): TopCities.City? {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addresses =
            geocoder.getFromLocation(positionClicked.latitude, positionClicked.longitude, 1)
        Log.d(this.javaClass.simpleName, "$addresses")
        if (!addresses.isNullOrEmpty()) {
            if (addresses[0].locality == null) {
                Toast.makeText(requireContext(), "No city name detected", Toast.LENGTH_LONG).show()
            } else {
                return TopCities.City(
                    name = addresses[0].locality ?: "",
                    latitude = addresses[0].latitude,
                    longitude = addresses[0].longitude
                )
            }
        }

        return null
    }

    private fun checkReadyThen(stuffToDo: () -> Unit) {
        if (!::map.isInitialized) {
            Toast.makeText(requireContext(), "The map is not ready", Toast.LENGTH_SHORT).show()
        } else {
            stuffToDo()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val actionModelCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.contextual_menu_map_fragment, menu)
            return true
        }

        override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                R.id.menu_item_save -> {
                    if (selectedCity == null) {
                        return false
                    }
                    viewModel.saveSelectedCity(selectedCity!!)
                    Toast.makeText(requireActivity(), "Selected city is saved", Toast.LENGTH_LONG).show()
                    mode.finish()
                    Handler(Looper.getMainLooper()).postDelayed({
                        findNavController().navigateUp()
                    }, 500L)
                    true
                }

                R.id.menu_item_cancel -> {
                    mode.finish()
                    true
                }

                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            actionMode = null
        }

    }
}