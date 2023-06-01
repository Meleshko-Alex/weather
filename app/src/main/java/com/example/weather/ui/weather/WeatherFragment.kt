package com.example.weather.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weather.MainActivity
import com.example.weather.common.Utils.getWeatherIcon
import com.example.weather.databinding.FragmentWeatherBinding
import com.example.weather.domain.models.weather.CurrentAndHourlyWeather
import com.example.weather.ui.State
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class WeatherFragment : Fragment() {
    private var _binding: FragmentWeatherBinding? = null
    private val binding: FragmentWeatherBinding get() = _binding!!
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).supportActionBar?.title = "Zaporizhzhia"
        viewModel.getWeather(latitude = 47.8378, longitude = 35.1383)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success -> {
                    hideLoading()
                    val weather = state.data!!
                    bindCurrentWeatherData(weather.current)
                    setUpEpoxyRecyclerView(weather.hourly)
                }
                is State.Error -> {
                    hideLoading()
                }
                is State.Loading -> {
                    showLoading()
                }
            }
        }
    }

    private fun setUpEpoxyRecyclerView(hourlyWeather: List<CurrentAndHourlyWeather.CurrentWeather>) {
        val epoxyController = WeatherHourlyEpoxyController()
        epoxyController.items = hourlyWeather
        binding.rvWeatherHourly.setController(epoxyController)
    }

    private fun bindCurrentWeatherData(currentWeather: CurrentAndHourlyWeather.CurrentWeather) {
//        binding.tvCityName.text = "Zaporizhzhia" // change to set dynamically
        binding.tvWeatherName.text = currentWeather.weather.weather.weatherName
        binding.tvDate.text = viewModel.convertEpochToLocalTime(currentWeather.time)
        binding.tvTemperature.text = currentWeather.temp.toInt().toString() + "°"
        binding.tvWind.text = currentWeather.windSpeed.toString() + " m/s"
        binding.tvFeelsLike.text = currentWeather.feelsLikeTemperature.toInt().toString() + "°"
        binding.tvIndexUv.text = currentWeather.uvi.toInt().toString()
        binding.tvHumidity.text = currentWeather.humidity.toString() + "%"
        binding.ivWeatherIcon.setImageResource(getWeatherIcon(currentWeather.weather))
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}