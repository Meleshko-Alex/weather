package com.example.weather.presentation.historical_weather

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.weather.MainActivity
import com.example.weather.R
import com.example.weather.databinding.FragmentHistoricalWeatherBinding
import com.example.weather.domain.models.cities.City
import com.example.weather.domain.models.weather.HistoricalWeather
import com.example.weather.presentation.BaseFragment
import com.example.weather.presentation.LoadingView
import com.example.weather.presentation.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class HistoricalWeatherFragment : BaseFragment(), LoadingView {
    private var _binding: FragmentHistoricalWeatherBinding? = null
    private val binding: FragmentHistoricalWeatherBinding get() = _binding!!
    private val viewModel: HistoricalWeatherViewModel by viewModels()
    private lateinit var calendarStart: Calendar
    private lateinit var calendarEnd: Calendar
    private lateinit var maxDate: Calendar
    private lateinit var city: City
    private lateinit var measurementUnit: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoricalWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpActionBar()
        lockNavigationDrawer()
        observeViewModel()
        setUpMenu()
        setUpStartDateField()
        setUpEndDateField()

        binding.btnGenerate.setOnClickListener {
            viewModel.getHistoricalDataRange(
                latitude = city.latitude,
                longitude = city.longitude,
                units = measurementUnit
            )
        }
    }

    private fun setUpStartDateField() {
        binding.atvStartDate.apply {
            setOnClickListener {
                if (viewModel.isEndDateTextInputLayoutEnabled.value) {
                    viewModel.setIsEndDateTextInputLayoutEnabled(false)
                    viewModel.setIsGenerateButtonEnabled(false)
                }
                showStartDatePickerDialog()
            }
            inputType = InputType.TYPE_NULL
        }
    }

    private fun setUpEndDateField() {
        binding.atvEndDate.apply {
            setOnClickListener { showEndDatePickerDialog() }
            inputType = InputType.TYPE_NULL
        }
    }

    private fun observeViewModel() {
        viewModel.userPref.observe(viewLifecycleOwner) {
            city = it.city
            measurementUnit = it.measurementUnit
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.startDate.collectLatest {
                    calendarStart = it
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.endDate.collectLatest {
                    calendarEnd = it
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isEndDateTextInputLayoutEnabled.collectLatest {
                    binding.tilEndDate.isEnabled = it
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isGenerateButtonEnabled.collectLatest {
                    binding.btnGenerate.isEnabled = it
                }
            }
        }

        viewModel.historicalWeatherState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success -> {
                    hideLoading()
                    binding.cardSparkInfo.visibility = View.VISIBLE
                    fillCardInfo(state.data!!)
                }

                is State.Loading -> {
                    displayLoading()
                    binding.cardSparkInfo.visibility = View.INVISIBLE
                }

                is State.Error -> {
                    hideLoading()
                    binding.cardSparkInfo.visibility = View.INVISIBLE
                    makeToast(requireContext(), state.message!!, Toast.LENGTH_LONG)
                }
            }
        }
    }

    private fun showStartDatePickerDialog() {
        val dp = DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                calendarStart.set(year, month, day)
                viewModel.setStartDate(calendarStart)
                binding.atvStartDate.apply {
                    setText(viewModel.getDateString(calendarStart))
                    clearFocus()
                }
                viewModel.setIsEndDateTextInputLayoutEnabled(true)
            },
            calendarStart.get(Calendar.YEAR),
            calendarStart.get(Calendar.MONTH),
            calendarStart.get(Calendar.DAY_OF_MONTH)
        )
        maxDate = Calendar.getInstance()
        maxDate.add(Calendar.DATE, -1)
        dp.datePicker.minDate = minDate
        dp.datePicker.maxDate = maxDate.timeInMillis
        dp.show()
    }

    private fun showEndDatePickerDialog() {
        val dp = DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                calendarEnd.set(year, month, day)
                viewModel.setEndDate(calendarEnd)
                binding.atvEndDate.apply {
                    setText(viewModel.getDateString(calendarEnd))
                    clearFocus()
                }
                viewModel.setIsGenerateButtonEnabled(true)
            },
            calendarEnd.get(Calendar.YEAR),
            calendarEnd.get(Calendar.MONTH),
            calendarEnd.get(Calendar.DAY_OF_MONTH)
        )

        // set 30 days later constraint i.e. a user has access to only next 0-30 days from the 'start date'
        maxDate.add(Calendar.DATE, 1)
        val thirtyDaysLater = calendarStart.clone() as Calendar
        thirtyDaysLater.add(Calendar.DATE, 30)
        val inMillis = thirtyDaysLater.timeInMillis

        dp.datePicker.maxDate =
            if (inMillis > maxDate.timeInMillis) maxDate.timeInMillis else inMillis
        dp.datePicker.minDate = viewModel.startDate.value.timeInMillis
        dp.show()
    }

    private fun setUpMenu() {
        (requireActivity() as MainActivity).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_historical_weather, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_item_info -> {
                        showInfoAlertDialog()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun showInfoAlertDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Info")
            .setMessage(
                """
                Build a graph that shows temperature change in the specified date range.
                 
                Select a start date. It can be any day starting from 1st January 1979 ('min date') till the day before today ('max date').
                Select an end date. Notice that you can only pick a day that is 30 days before, or after, the start day.
                 
                Tap on the 'Generate' button to generate the graph.
                
                Tap and hold on the graph to get the corresponding value. 
                
                Fields in the graph:
                    - Average temp: the average temperature in the date range.
                    - Max temp (pivot's upper value): the maximum temperature in the date range.
                    - Min temp (pivot's lower value): the minimum temperature in the date range.
                    - Date range: the specified date range.
            """.trimIndent()
            )
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun fillCardInfo(data: List<HistoricalWeather>) {
        buildGraph(data)
        val startDate = viewModel.startDate.value
        val endDate = viewModel.endDate.value
        binding.tvDateRange.text = getString(
            R.string.date_range,
            "${startDate.get(Calendar.DAY_OF_MONTH)}.${startDate.get(Calendar.MONTH) + 1}",
            "${endDate.get(Calendar.DAY_OF_MONTH)}.${endDate.get(Calendar.MONTH) + 1}"
        )
        binding.tvAvgTemp.text = getString(R.string.temperature, viewModel.getAverageTemp(data))
        binding.tvMaxTemp.text =
            getString(R.string.temperature, data.sortedBy { it.temp }[data.size - 1].temp)
        binding.tvMinTemp.text = getString(R.string.temperature, data.sortedBy { it.temp }[0].temp)
    }

    private fun buildGraph(data: List<HistoricalWeather>) {
        binding.sparkGraph.apply {
            adapter = MySparkAdapter(data)
            lineColor = ContextCompat.getColor(requireContext(), R.color.white)
            isScrubEnabled = true
            setScrubListener {
                if (it != null) {
                    binding.tvScrubbedText.text = it.toString()
                }
            }
        }
    }

    override fun setUpActionBar() {
        // change Up button icon
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_left_blue)
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

    companion object {
        private const val minDate = 284083200000L // 2nd January 1979
    }
}