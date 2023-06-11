package com.example.weather.presentation.registration

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weather.MainActivity
import com.example.weather.R
import com.example.weather.common.SharedPref
import com.example.weather.databinding.FragmentRegistrationBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider

class RegistrationFragment : Fragment() {
    private val TAG = this.javaClass.simpleName
    private var _binding: FragmentRegistrationBinding? = null
    private val binding: FragmentRegistrationBinding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (auth.currentUser != null || SharedPref(requireContext()).getIsGuest()) {
            Log.d(this.javaClass.simpleName, "onCreateView: " + SharedPref(requireActivity()).getIsGuest().toString())
            findNavController().navigate(R.id.action_registrationFragment_to_homeWeatherFragment)
        }
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpActionBar()
        setUpStatusBar()
        signInWithGoogle()
        signInWithTwitter()
        continueWithoutAccount()
    }

    private fun setUpActionBar() {
        (requireActivity() as MainActivity).supportActionBar?.hide()
    }

    private fun setUpStatusBar() {
        requireActivity().window.apply {
            // change background color
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)

            // change  text color
            WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars = true

            // change navigation bar background color
            navigationBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        }
    }

    private fun signInWithGoogle() {
        binding.btnSignInGoogle.setOnClickListener {
            val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestProfile()
                .requestEmail()
                .build()
            val signInClient = GoogleSignIn.getClient(requireActivity(), options)
            intentLauncher.launch(signInClient.signInIntent)
        }
    }

    private fun signInWithTwitter() {
        binding.btnSignInTwitter.setOnClickListener {
            val provider = OAuthProvider.newBuilder("twitter.com")
            val pendingResultTask = auth.pendingAuthResult
            if (pendingResultTask != null) {
                pendingResultTask
                    .addOnSuccessListener {
                        Toast.makeText(requireActivity(), getString(R.string.successful_authentication), Toast.LENGTH_LONG).show()
                        SharedPref(requireActivity()).setIsGuest(false)
                        findNavController().navigate(R.id.action_registrationFragment_to_homeWeatherFragment)
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
            } else {
                auth
                    .startActivityForSignInWithProvider(requireActivity(), provider.build())
                    .addOnSuccessListener {
                        Toast.makeText(requireActivity(), getString(R.string.successful_authentication), Toast.LENGTH_LONG).show()
                        SharedPref(requireActivity()).setIsGuest(false)
                        findNavController().navigate(R.id.action_registrationFragment_to_homeWeatherFragment)
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
            }
        }
    }

    private fun continueWithoutAccount() {
        binding.btnNoAccount.setOnClickListener {
            SharedPref(requireActivity()).setIsGuest(true)
            findNavController().navigate(R.id.action_registrationFragment_to_homeWeatherFragment)
        }
    }

    private val intentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                handleResult(GoogleSignIn.getSignedInAccountFromIntent(result.data))
            }
        }

    private fun handleResult(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account = task.result
            if (account != null) {
                val authCredential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(authCredential)
                    .addOnCompleteListener { result ->
                        if (result.isSuccessful) {
                            Toast.makeText(requireActivity(), getString(R.string.successful_authentication), Toast.LENGTH_LONG).show()
                            SharedPref(requireActivity()).setIsGuest(false)
                            findNavController().navigate(R.id.action_registrationFragment_to_homeWeatherFragment)
                        } else {
                            Toast.makeText(requireContext(), result.exception?.message, Toast.LENGTH_LONG).show()
                        }
                    }
            }
        } else {
            Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}