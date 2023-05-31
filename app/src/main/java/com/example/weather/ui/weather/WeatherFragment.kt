package com.example.weather.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weather.databinding.FragmentWeatherBinding
import com.example.weather.domain.models.weather.CurrentAndHourlyWeather
import com.example.weather.ui.State
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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
        viewModel.getWeather(latitude = 47.8378, longitude = 35.1383)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success -> {
                    hideLoading()
                    bindCurrentWeatherData(state.data!!.current)
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

    private fun bindCurrentWeatherData(currentWeather: CurrentAndHourlyWeather.CurrentWeather) {
        binding.tvCityName.text = "Zaporizhzhia" // change to set dynamically
        binding.tvWeatherName.text = currentWeather.weather.weather.weatherName
        val currentDate = LocalDateTime.now()
        val formatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("EEEE, d MMM", Locale.ENGLISH)
        val formattedDate = currentDate.format(formatter)
        binding.tvDate.text = formattedDate
        binding.tvTemperature.text = currentWeather.temp.toInt().toString() + "°"
        binding.tvWind.text = currentWeather.windSpeed.toString() + " metre/sec"
        binding.tvFeelsLike.text = currentWeather.feelsLikeTemperature.toInt().toString() + "°"
        binding.tvIndexUv.text = currentWeather.uvi.toInt().toString()
        binding.tvHumidity.text = currentWeather.humidity.toString() + "%"
        binding.ivWeatherIcon.setImageResource(currentWeather.weather.icon.iconNormal)
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