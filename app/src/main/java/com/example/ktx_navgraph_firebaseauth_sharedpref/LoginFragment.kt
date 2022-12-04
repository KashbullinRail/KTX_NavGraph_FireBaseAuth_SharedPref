package com.example.ktx_navgraph_firebaseauth_sharedpref


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ktx_navgraph_firebaseauth_sharedpref.databinding.FragmentLoginBinding



class LoginFragment : Fragment() {

    private val viewModel by viewModels<LoginViewModel>()
    private var _binding: FragmentLoginBinding? = null

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

        observeAuthentificationState()

        _binding?.btnWithoutLogin?.setOnClickListener {
            findNavController().navigate(R.id.congratulationsFragment)
        }

        _binding?.btnCreateAccount?.setOnClickListener {
            findNavController().navigate(R.id.registrationFragment)
        }

    }

    private fun observeAuthentificationState() {
        viewModel.authenticationState.observe(viewLifecycleOwner, { authenticationState ->
            when(authenticationState){
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    _binding?.btnLogin?.setOnClickListener {
                        findNavController().navigate(R.id.congratulationsFragment)
                    }
                } else -> {
                    _binding?.btnLogin?.setOnClickListener {
                        findNavController().navigate(R.id.loginFragment)
                    }
                }
            }
        })
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}