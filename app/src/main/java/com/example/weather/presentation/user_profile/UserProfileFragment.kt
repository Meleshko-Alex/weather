package com.example.weather.presentation.user_profile

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.weather.R
import com.example.weather.common.Constants
import com.example.weather.common.SharedPref
import com.example.weather.databinding.FragmentUserProfileBinding
import com.example.weather.presentation.main.BaseFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : BaseFragment() {
    private var _binding: FragmentUserProfileBinding? = null
    private val binding: FragmentUserProfileBinding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()
    private val viewModel: UserProfileViewModel by viewModels()
    private lateinit var checkedUnit: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpActionBar()
        lockNavigationDrawer()
        bindUserData()
        observeViewModel()

        binding.cardWeatherSource.setOnClickListener {
            showWeatherDataSource()
        }

        binding.cardMeasurementUnit.setOnClickListener {
            setUpMeasurementUnitSelectionDialog().show()
        }

        binding.cardLogout.setOnClickListener {
            logout()
        }

        binding.cardSignIn.setOnClickListener {
           goToRegistrationFragment()
        }
    }

    private fun showWeatherDataSource() {
        requireActivity().startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(SOURCE_URL)
            )
        )
    }

    private fun bindUserData() {
        if (auth.currentUser == null) {
            binding.userInfo.visibility = View.GONE
            binding.cardLogout.visibility = View.GONE
            binding.cardSignIn.visibility = View.VISIBLE
        } else {
            binding.userInfo.visibility = View.VISIBLE
            binding.cardLogout.visibility = View.VISIBLE
            binding.cardSignIn.visibility = View.GONE
            auth.currentUser.apply {
                binding.tvUserName.text = this?.displayName
                binding.tvUserEmail.text = this?.email
                Glide.with(requireContext()).load(this?.photoUrl).into(binding.ivUserProfilePicture)
            }
        }
    }

    override fun setUpActionBar() {
        // change Up button icon
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_left_blue)
    }

    private fun observeViewModel() {
        viewModel.unit.observe(viewLifecycleOwner) {
            checkedUnit = it
            binding.tvMeasurementUnit.text = it
        }
    }

    private fun setUpMeasurementUnitSelectionDialog(): AlertDialog {
        val items = arrayOf(Constants.MeasurementsUnits.METRIC.value, Constants.MeasurementsUnits.IMPERIAL.value)
        return AlertDialog.Builder(requireActivity())
            .setTitle("Select units of measurement")
            .setSingleChoiceItems(items, items.indexOf(checkedUnit)) { dialog, selectedItem ->
                viewModel.setMeasurementUnit(items[selectedItem])
                dialog.dismiss()
            }
            .create()
    }

    private fun logout() {
        if (auth.currentUser != null) {
            auth.signOut()

            // to enable Google account selection pop up window
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
            googleSignInClient.signOut()

            Toast.makeText(requireContext(), "Successfully signed out", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Sign in first", Toast.LENGTH_LONG).show()
        }
        SharedPref(requireActivity()).setIsGuest(false)
        findNavController().navigate(R.id.action_userProfileFragment_to_registrationFragment)
    }

    private fun goToRegistrationFragment() {
        SharedPref(requireActivity()).setIsGuest(false)
        findNavController().navigate(R.id.action_userProfileFragment_to_registrationFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val SOURCE_URL = "https://openweathermap.org/"
    }
}