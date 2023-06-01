package com.example.weather.ui.weather_hourly

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.weather.MainActivity
import com.example.weather.R
import com.example.weather.common.Utils.convertEpochToLocalDate
import com.example.weather.common.Utils.getWeatherIcon
import com.example.weather.databinding.FragmentHourlyWeatherFlatBinding
import com.example.weather.domain.models.weather.HourlyWeather
import com.example.weather.ui.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HourlyWeatherFragment : Fragment() {
//    private var _binding: FragmentHourlyWeatherBinding? = null
//    private val binding: FragmentHourlyWeatherBinding get() = _binding!!
    private var _binding: FragmentHourlyWeatherFlatBinding? = null
    private val binding: FragmentHourlyWeatherFlatBinding get() = _binding!!
    private val viewModel: HourlyWeatherViewModel by viewModels()
    private val epoxyController = HourlyWeatherEpoxyController()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHourlyWeatherFlatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actionBar = (requireActivity() as MainActivity).supportActionBar
        actionBar?.title = "Zaporizhzhia"
        actionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(requireContext(), R.color.white)
            )
        )

        val window: Window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        val decorView = window.decorView
        val wic = WindowInsetsControllerCompat(window, decorView)
        wic.isAppearanceLightStatusBars = true

        setUpEpoxyRecyclerView()

        viewModel.getWeatherData(latitude = 47.8378, longitude = 35.1383)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success -> {
                    hideLoading()
                    val weather = state.data!!
                    bindWeatherData(weather.current)
                    epoxyController.items = weather.hourly
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

    private fun setUpEpoxyRecyclerView() {
        binding.rvWeatherHourly.setController(epoxyController)
    }

    private fun bindWeatherData(currentWeather: HourlyWeather.CurrentWeather) {
//        binding.tvCityName.text = "Zaporizhzhia" // change to set dynamically
        binding.tvWeatherName.text = currentWeather.weather.weather.weatherName
        binding.tvDate.text = convertEpochToLocalDate(currentWeather.timeDate)
        binding.tvTemperature.text = currentWeather.temp.toString() + "°"
        binding.tvWind.text = currentWeather.windSpeed.toString() + " m/s"
        binding.tvFeelsLike.text = currentWeather.feelsLikeTemperature.toString() + "°"
        binding.tvIndexUv.text = currentWeather.uvi.toString()
        binding.tvHumidity.text = currentWeather.humidity.toString() + "%"
        binding.ivWeatherIcon.setImageResource(getWeatherIcon(currentWeather.weather))
        binding.tvNext7Days.setOnClickListener {
            findNavController().navigate(R.id.action_weatherFragment_to_dailyWeatherFragment)
        }
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