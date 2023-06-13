package com.example.weather.presentation.temperature_graph

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.room.util.wrapMappedColumns
import com.example.weather.databinding.FragmentTemperatureGraphBinding
import com.example.weather.domain.models.cities.City
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import kotlin.math.max

@AndroidEntryPoint
class TemperatureGraphFragment : Fragment() {
    private var _binding: FragmentTemperatureGraphBinding? = null
    private val binding: FragmentTemperatureGraphBinding get() = _binding!!
    private val viewModel: TempGraphViewModel by viewModels()
    private val calendarStart = Calendar.getInstance()
    private val calendarFinish = Calendar.getInstance()
    private val format = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    private val minDate = 284083200000L // 2nd January 1979
    private val maxDate = Calendar.getInstance().timeInMillis
    private lateinit var city: City
    private lateinit var measurementUnit: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTemperatureGraphBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userPref.observe(viewLifecycleOwner) {
            city = it.city
            measurementUnit = it.measurementUnit
        }
        viewModel.startDate.observe(viewLifecycleOwner) {
            binding.tvStartDate.text = viewModel.getDateString(it)
        }
        viewModel.finishDate.observe(viewLifecycleOwner) {
            binding.tvFinishDate.text = viewModel.getDateString(it)
        }
        binding.btnSelectStartDate.setOnClickListener {
            showStartDatePickerDialog()
        }
        binding.btnSelectFinishDate.setOnClickListener {
            showFinishDatePickerDialog()
        }
        binding.btnGenerate.setOnClickListener {
            viewModel.getHistoricalData(
                latitude = city.latitude,
                longitude = city.longitude,
                units = measurementUnit
            )
        }
        viewModel.a.observe(viewLifecycleOwner) {
            binding.tvScrubbedText.text = it
        }
    }

    private fun showStartDatePickerDialog() {
        val dp = DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                calendarStart.set(year, month, day)
                viewModel.setStartDate(calendarStart)
            },
            calendarStart.get(Calendar.YEAR),
            calendarStart.get(Calendar.MONTH),
            calendarStart.get(Calendar.DAY_OF_MONTH)
        )
        dp.datePicker.minDate = minDate
        dp.datePicker.maxDate = maxDate
        dp.show()
    }

    private fun showFinishDatePickerDialog() {
        val dp = DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                calendarFinish.set(year, month, day)
                viewModel.setFinishDate(calendarFinish)
            },
            calendarFinish.get(Calendar.YEAR),
            calendarFinish.get(Calendar.MONTH),
            calendarFinish.get(Calendar.DAY_OF_MONTH)
        )

        dp.datePicker.minDate = if (viewModel.startDate.value == null) {
            minDate
        } else {
            viewModel.startDate.value!!.timeInMillis
        }

        // set 30 days later constraint i.e. a user has access to only next 0-30 days from the 'start date'
        dp.datePicker.maxDate = if (viewModel.startDate.value == null) {
            maxDate
        } else {
            val thirtyDaysLater = calendarStart.clone() as Calendar
            thirtyDaysLater.add(Calendar.DATE, 30)
            val inMillis = thirtyDaysLater.timeInMillis
            if (inMillis > maxDate) maxDate else inMillis
        }
        dp.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}