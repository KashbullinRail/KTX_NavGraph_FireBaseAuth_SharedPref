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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        auth = Firebase.auth

        fun snakbar(id: Int) {
            Snackbar.make(view, requireActivity().getString(id),
                Snackbar.LENGTH_LONG).show()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navController.popBackStack(R.id.loginFragment, false)
        }

        _binding?.btnRegistration?.setOnClickListener {

            if (_binding?.etEmailReg?.text.toString()
                    .isEmpty() || _binding?.etPasswordReg?.text.toString().isEmpty()
            ) {
                snakbar(R.string.emailPasswordEmpty)
            } else {
                auth.createUserWithEmailAndPassword(
                    _binding?.etEmailReg?.text.toString(),
                    _binding?.etPasswordReg?.text.toString()
                ).addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        snakbar(R.string.registration_success)
                        findNavController().navigate(R.id.loginFragment)
                    } else {
                        snakbar(R.string.registration_failed)
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