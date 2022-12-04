package com.example.ktx_navgraph_firebaseauth_sharedpref


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings.Global.putString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ktx_navgraph_firebaseauth_sharedpref.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {

    private val viewModel by viewModels<LoginViewModel>()
    private var _binding: FragmentLoginBinding? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        this._binding = binding
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        observeAuthentificationState()

        _binding?.btnWithoutLogin?.setOnClickListener {
            findNavController().navigate(R.id.congratulationsFragment)
        }

        _binding?.btnCreateAccount?.setOnClickListener {
            findNavController().navigate(R.id.registrationFragment)
        }

        auth = Firebase.auth


        _binding?.btnLogin?.setOnClickListener {
            if (_binding?.etEmail?.text.toString()
                    .isEmpty() || _binding?.etPassword?.text.toString().isEmpty()
            ) {
               observeAuthentificationState()
            } else {
                auth.signInWithEmailAndPassword(
                    _binding?.etEmail?.text.toString(),
                    _binding?.etPassword?.text.toString()
                )
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            findNavController().navigate(R.id.congratulationsFragment)
                        }
                    }
            }
        }

    }

    private fun observeAuthentificationState() {
        viewModel.authenticationState.observe(viewLifecycleOwner) { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    _binding?.btnLogin?.setOnClickListener { View ->
                        findNavController().navigate(R.id.congratulationsFragment)
                    }
                }
                else -> {
                    _binding?.btnLogin?.setOnClickListener { View ->
                        findNavController().navigate(R.id.registrationFragment)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun snakbar(id: Int, view: View) {
        Snackbar.make(view, requireActivity().getString(id),
            Snackbar.LENGTH_LONG).show()
    }

}