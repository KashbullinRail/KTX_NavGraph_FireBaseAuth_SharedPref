package com.example.ktx_navgraph_firebaseauth_sharedpref

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class GmailRegistrationFragment : Fragment() {

    companion object {
        const val TAG = "LoginSuccess"
        const val SIGN_IN_CODE = 5000
    }

    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var navController: NavController
    private val btnGmailReg:Button by lazy { requireActivity().findViewById(R.id.btnGmailReg) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gmail_registration, container, false)
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
                .build(), GmailRegistrationFragment.SIGN_IN_CODE
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

        fun snakbar(id: Int) {
            Snackbar.make(view, requireActivity().getString(id),
                Snackbar.LENGTH_LONG).show()
        }

        btnGmailReg.setOnClickListener { launchSignInFlow() }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navController.popBackStack(R.id.loginFragment, false)
        }

        viewModel.authenticationState.observe(viewLifecycleOwner) { authentificationState ->
            when (authentificationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    navController.navigate(R.id.congratulationsFragment)
                }
                LoginViewModel.AuthenticationState.INVALID_AUTHENTICATION -> Snackbar.make(
                    view, requireActivity().getString(R.string.authentification_fail),
                    Snackbar.LENGTH_LONG
                ).show()
                else -> Log.d(TAG, "RRRRRRR")
            }
        }

    }

}