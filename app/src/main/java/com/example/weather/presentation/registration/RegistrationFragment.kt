package com.example.weather.presentation.registration

import android.app.Activity
import android.os.Bundle
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
import com.example.weather.databinding.FragmentRegistrationBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class RegistrationFragment : Fragment() {
    private val TAG = this.javaClass.simpleName
    private var _binding: FragmentRegistrationBinding? = null
    private val binding: FragmentRegistrationBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().apply {
            onBackPressedDispatcher.addCallback(this) {
                finish()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpActionBar()
        setUpStatusBar()
        signInWithGoogle()

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
                .build()
            val signInClient = GoogleSignIn.getClient(requireActivity(), options)
            intentLauncher.launch(signInClient.signInIntent)
        }
    }

    private fun signInWithFacebook() {

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
                FirebaseAuth.getInstance().signInWithCredential(authCredential)
                    .addOnCompleteListener { result ->
                        if (result.isSuccessful) {
                            Toast.makeText(
                                requireActivity(),
                                getString(R.string.successful_authentication),
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().navigateUp()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                result.exception.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        } else {
            Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}