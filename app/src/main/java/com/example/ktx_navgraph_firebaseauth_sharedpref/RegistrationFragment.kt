package com.example.ktx_navgraph_firebaseauth_sharedpref

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.ktx_navgraph_firebaseauth_sharedpref.databinding.FragmentRegistrationBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegistrationFragment : Fragment() {

    private lateinit var navController: NavController
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
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

        binding.btnRegistration.setOnClickListener {

            if (binding.etEmailReg.text.toString()
                    .isEmpty() || binding.etPasswordReg.text.toString().isEmpty()
            ) {
                snakbar(R.string.emailPasswordEmpty)
            } else {
                auth.createUserWithEmailAndPassword(
                    binding.etEmailReg.text.toString(),
                    binding.etPasswordReg.text.toString()
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