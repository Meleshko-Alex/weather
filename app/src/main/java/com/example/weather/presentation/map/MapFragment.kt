package com.example.weather.presentation.map

import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.weather.R
import com.example.weather.domain.models.cities.City
import com.example.weather.databinding.FragmentMapBinding
import com.example.weather.presentation.BaseFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MapFragment : BaseFragment(), OnMapReadyCallback, OnMapClickListener, OnMarkerClickListener {
    private var _binding: FragmentMapBinding? = null
    private val binding: FragmentMapBinding get() = _binding!!
    private val args: MapFragmentArgs by navArgs()
    private lateinit var currentCity: City
    private lateinit var map: GoogleMap
    private val viewModel: MapViewModel by viewModels()
    private var selectedCity: City? = null
    private var actionMode: ActionMode? = null
    private var lastSelectedMarker: Marker? = null
    private val TAG = this.javaClass.simpleName

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
        currentCity = Gson().fromJson(args.currentCity, City::class.java)
        setUpActionBar()
        lockNavigationDrawer()
        setUpMapFragment()
    }

    private fun setUpMapFragment() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun setUpActionBar() {
        // change Up button icon
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_left_blue)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val currentPosition = LatLng(currentCity.latitude, currentCity.longitude)
        val marker = googleMap.addMarker(
            MarkerOptions()
                .position(currentPosition)
                .title(currentCity.name)
        )
        lastSelectedMarker = marker
        marker?.showInfoWindow()
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 10f))
        googleMap.setOnMapClickListener(this)
        googleMap.setOnMarkerClickListener(this)
    }

    override fun onMapClick(positionClicked: LatLng) {
        checkReadyThen {
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(positionClicked, 10f)
            val clickedCity = getClickedCityPosition(positionClicked)
            if (clickedCity != null) {
                Log.d(TAG, "$clickedCity")
                selectedCity = clickedCity
                lastSelectedMarker?.remove()
                lastSelectedMarker = map.addMarker(
                    MarkerOptions()
                        .position(positionClicked)
                        .title(selectedCity?.name)
                )
                lastSelectedMarker?.showInfoWindow()
                map.moveCamera(cameraUpdate)

                if (actionMode == null) {
                    actionMode = requireActivity().startActionMode(actionModelCallback)
                }
            } else {
                actionMode?.finish()
                lastSelectedMarker?.remove()
            }
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val city = getClickedCityPosition(marker.position)
        city?.let {
            Log.d(TAG, "$it")
            selectedCity = it
            if (actionMode == null) {
                actionMode = requireActivity().startActionMode(actionModelCallback)
            }
        }

        return false
    }

    private fun getClickedCityPosition(positionClicked: LatLng): City? {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addresses =
            geocoder.getFromLocation(positionClicked.latitude, positionClicked.longitude, 1)
        Log.d(TAG, "$addresses")
        if (!addresses.isNullOrEmpty()) {
            if (addresses[0].locality == null) {
                makeToast(
                    requireContext(),
                    "No city detected. Pick another place or zoom in more and try again",
                    Toast.LENGTH_LONG
                )
            } else {
                return City(
                    name = addresses[0].locality,
                    latitude = addresses[0].latitude,
                    longitude = addresses[0].longitude
                )
            }
        }

        return null
    }

    private fun checkReadyThen(stuffToDo: () -> Unit) {
        if (!::map.isInitialized) {
            makeToast(requireContext(), "The map is not ready", Toast.LENGTH_SHORT)
        } else {
            stuffToDo.invoke()
        }
    }

    private val actionModelCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.contextual_menu_map_fragment, menu)
            mode?.title = "Save selected city?"
            actionBar?.hide()
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
                    Toast.makeText(requireActivity(), "Saved selected city", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_mapFragment_to_homeWeatherFragment)
                    mode.finish()
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
            Handler(Looper.getMainLooper()).postDelayed({
                actionBar?.show()
            }, 300L)
            actionMode = null
//            actionBar?.show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}