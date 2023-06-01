package com.example.weather.ui.weather_daily

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weather.MainActivity
import com.example.weather.R
import com.example.weather.databinding.FragmentDailyWeatherBinding
import com.example.weather.ui.State
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DailyWeatherFragment : Fragment() {
    private var _binding: FragmentDailyWeatherBinding? = null
    private val binding: FragmentDailyWeatherBinding get() = _binding!!
    private val viewModel: DailyWeatherViewModel by viewModels()
    private val epoxyController = DailyWeatherEpoxyController()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actionBar = (requireActivity() as MainActivity).supportActionBar
        val actionBarTitleColor = ContextCompat.getColor(requireContext(), R.color.white)
        val titleSpannable = SpannableString("Zaporizhzhia")
        titleSpannable.setSpan(
            ForegroundColorSpan(actionBarTitleColor),
            0,
            titleSpannable.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        actionBar?.title = titleSpannable
        actionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(requireContext(), R.color.blue)
            )
        )

        val window: Window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.blue)
        val decorView = window.decorView
        val wic = WindowInsetsControllerCompat(window, decorView)
        wic.isAppearanceLightStatusBars = false

        actionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_left)

        setUpEpoxyRecyclerView()
        viewModel.getWeatherData(latitude = 47.8378, longitude = 35.1383)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success -> {
                    val weather = state.data!!
                    epoxyController.items = weather.daily
                }

                is State.Error -> {

                }

                is State.Loading -> {

                }
            }
        }
    }

    private fun setUpEpoxyRecyclerView() {
        binding.rvDailyWeather.setController(epoxyController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}