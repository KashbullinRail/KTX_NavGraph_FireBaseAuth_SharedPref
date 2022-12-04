package com.example.ktx_navgraph_firebaseauth_sharedpref

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.ktx_navgraph_firebaseauth_sharedpref.databinding.FragmentRegistrationBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    companion object {
        const val TAG = "LoginSuccess"
        const val SIGN_IN_CODE = 5000
    }

    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var navController: NavController
    private var _binding: FragmentRegistrationBinding? = null
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        this._binding = binding
        return binding.root
    }

    private fun launchSignInFlow() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), SIGN_IN_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SIGN_IN_CODE) {
            val responce = IdpResponse.fromResultIntent(data)
            if (requestCode == Activity.RESULT_OK) {
                Log.d(
                    TAG, "Success sign in user" +
                            "${FirebaseAuth.getInstance().currentUser?.displayName}"
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        auth = Firebase.auth

        _binding?.btnRegistrationOnGoogle?.setOnClickListener { launchSignInFlow() }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navController.popBackStack(R.id.loginFragment, false)
        }

        viewModel.authenticationState.observe(viewLifecycleOwner) { authentificationState ->
            when (authentificationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    navController.popBackStack()
                    navController.navigate(R.id.congratulationsFragment)
                }
                LoginViewModel.AuthenticationState.INVALID_AUTHENTICATION -> Snackbar.make(
                    view, requireActivity().getString(R.string.authentification_fail),
                    Snackbar.LENGTH_LONG
                ).show()
                else -> Log.d(TAG, "Authentification state that does not $authentificationState")
            }
        }

        _binding?.btnRegistration?.setOnClickListener {

            if (_binding?.etEmailReg?.text.toString()
                    .isEmpty() || _binding?.etPasswordReg?.text.toString().isEmpty()
            ) {

            } else {
                auth.createUserWithEmailAndPassword(
                    _binding?.etEmailReg?.text.toString(),
                    _binding?.etPasswordReg?.text.toString()
                ).addOnCompleteListener(requireActivity()) { task ->

                    if (task.isSuccessful) {
                        findNavController().navigate(R.id.loginFragment)
                    } else {

                    }
                }
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}