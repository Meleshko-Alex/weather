package com.example.weather.presentation.user_profile

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.weather.MainActivity
import com.example.weather.R
import com.example.weather.common.Constants
import com.example.weather.databinding.FragmentUserProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : Fragment() {
    private var _binding: FragmentUserProfileBinding? = null
    private val binding: FragmentUserProfileBinding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()
    private val viewModel: UserProfileViewModel by viewModels()
    private lateinit var checkedUnit: String
    private lateinit var alertDialog: AlertDialog


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
//        setUpMenu()
        bindUserData()
        observeViewModel()

        binding.cardWeatherSource.setOnClickListener {
            requireActivity().startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.SOURCE_URL)))
        }
        binding.cardMeasurementUnit.setOnClickListener {
            setUpMeasurementUnitSelectionDialog().show()
        }
        binding.cardLogout.setOnClickListener {
            logout()
        }
    }

    private fun bindUserData() {
        auth.currentUser.apply {
            binding.tvUserName.text = this?.displayName
            binding.tvUserEmail.text = this?.email
            Glide.with(requireContext()).load(this?.photoUrl).into(binding.ivUserProfilePicture)
        }
    }

    private fun setUpActionBar() {
        (requireActivity() as MainActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_left_blue)
    }

    private fun observeViewModel() {
        viewModel.unit.observe(viewLifecycleOwner) {
            checkedUnit = it
            binding.tvMeasurementUnit.text = it
        }
    }

    private fun setUpMeasurementUnitSelectionDialog(): AlertDialog {
        val items = arrayOf(Constants.MEASURE_UNIT_METRIC, Constants.MEASURE_UNIT_IMPERIAL)
        return AlertDialog.Builder(requireActivity())
            .setTitle("Select units of measurement")
            .setSingleChoiceItems(items, items.indexOf(checkedUnit)) { dialog, selectedItem ->
                viewModel.setMeasurementUnit(items[selectedItem])
                dialog.dismiss()
            }
            .create()
    }

    private fun setUpMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_user_profile, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_item_sign_out -> {
                        logout()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun logout() {
        if (auth.currentUser != null) {
            // check sign in method i.e. google or facebook
            //sign out from google
            auth.signOut()
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
            googleSignInClient.signOut()


            Toast.makeText(requireContext(), "Successfully signed out", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_userProfileFragment_to_registrationFragment)
        } else {
            Toast.makeText(requireContext(), "Sign in first", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}