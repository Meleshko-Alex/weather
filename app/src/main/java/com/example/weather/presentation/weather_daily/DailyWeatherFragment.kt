package com.example.weather.presentation.weather_daily

import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.viewModels
import com.example.weather.MainActivity
import com.example.weather.R
import com.example.weather.domain.models.cities.City
import com.example.weather.databinding.FragmentDailyWeatherBinding
import com.example.weather.presentation.BaseFragment
import com.example.weather.presentation.LoadingView
import com.example.weather.presentation.State
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DailyWeatherFragment : BaseFragment(), LoadingView {
    private var _binding: FragmentDailyWeatherBinding? = null
    private val binding: FragmentDailyWeatherBinding get() = _binding!!
    private val viewModel: DailyWeatherViewModel by viewModels()
    private val epoxyController = DailyWeatherEpoxyController()
    private lateinit var city: City
    private lateinit var measurementUnit: String
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
        lockNavigationDrawer()
        setProgressBarColor()
        observeViewModel()
        setUpEpoxyRecyclerView()
    }

    private fun observeViewModel() {
        viewModel.userPref.observe(viewLifecycleOwner) {
            city = it.city
            measurementUnit = it.measurementUnit
            setActionBarTitle()
            getWeatherData()
        }

        viewModel.weatherState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success -> {
                    hideLoading()
                    val weather = state.data!!
                    epoxyController.items = weather.daily
                }

                is State.Error -> {
                    makeToast(requireContext(), state.message?: "Unknown error", Toast.LENGTH_LONG)
                    hideLoading()
                }

                is State.Loading -> {
                    displayLoading()
                }
            }
        }
    }

    private fun setActionBarTitle() {
        actionBar?.apply {
            val actionBarTitleColor = ContextCompat.getColor(requireContext(), R.color.white)
            val titleSpannable = SpannableString(city.name)
            titleSpannable.setSpan(
                ForegroundColorSpan(actionBarTitleColor),
                0,
                titleSpannable.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            title = titleSpannable
        }
    }

    override fun setUpActionBar() {
        actionBar?.apply {
            // change background color
            setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(requireContext(), R.color.blue)))

            // change Up button icon
            setHomeAsUpIndicator(R.drawable.ic_arrow_left_white)
        }
    }

    override fun setUpStatusBar() {
        requireActivity().window.apply {
            // change background color
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = ContextCompat.getColor(requireContext(), R.color.blue)

            // change text color
            WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars = false

            // change navigation bar background color
            navigationBarColor = ContextCompat.getColor(requireContext(), R.color.blue)
        }
    }

    private fun setProgressBarColor() {
        val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white))
        binding.loadingIndicator.progressCircular.apply {
            progressTintList = colorStateList
            indeterminateTintList = colorStateList
        }
    }

    private fun getWeatherData() {
        viewModel.getDailyWeatherData()
    }

    private fun setUpEpoxyRecyclerView() {
        binding.rvDailyWeather.setController(epoxyController)
    }

    override fun displayLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}