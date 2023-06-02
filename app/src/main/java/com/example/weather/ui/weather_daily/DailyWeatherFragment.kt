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
        setUpActionBar()
        setUpStatusBar()
        setUpEpoxyRecyclerView()
        getWeatherData()
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success -> {
                    hideLoading()
                    val weather = state.data!!
                    epoxyController.items = weather.daily
                }

                is State.Error -> {
                    hideLoading()
                }

                is State.Loading -> {
                    showLoading()
                }
            }
        }
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                getWeatherData()
                isRefreshing = false
            }
        }
    }

    private fun setUpActionBar() {
        (requireActivity() as MainActivity).supportActionBar?.apply {
            // change title color
            val actionBarTitleColor = ContextCompat.getColor(requireContext(), R.color.white)
            val titleSpannable = SpannableString("Zaporizhzhia")
            titleSpannable.setSpan(
                ForegroundColorSpan(actionBarTitleColor),
                0,
                titleSpannable.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            title = titleSpannable

            // change background color
            setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(requireContext(), R.color.blue)
                )
            )

            setHomeAsUpIndicator(R.drawable.ic_arrow_left)
        }
    }

    private fun setUpStatusBar() {
        requireActivity().window.apply {
            // change background color
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = ContextCompat.getColor(requireContext(), R.color.blue)

            // change text color
            WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars = false
        }
    }

    private fun getWeatherData() {
        viewModel.getWeatherData(latitude = 47.8378, longitude = 35.1383)
    }

    private fun setUpEpoxyRecyclerView() {
        binding.rvDailyWeather.setController(epoxyController)
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