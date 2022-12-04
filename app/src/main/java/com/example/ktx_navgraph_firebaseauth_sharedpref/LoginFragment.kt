package com.example.ktx_navgraph_firebaseauth_sharedpref

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    private val etEmail: EditText by lazy { requireActivity().findViewById(R.id.etEmail) }
    private val etPassword: EditText by lazy { requireActivity().findViewById(R.id.etPassword) }
    private val btnLogin: Button by lazy { requireActivity().findViewById(R.id.btnLogin) }
    private val btnCreateAccount: Button by lazy { requireActivity().findViewById(R.id.btnCreateAccount) }
    private val btnWithoutLogin: Button by lazy { requireActivity().findViewById(R.id.btnWithoutLogin) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        btnCreateAccount.setOnClickListener {
            findNavController().navigate(R.layout.fragment_registration, null)
        }

        btnWithoutLogin.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections
                    .actionLoginFragmentToCongratulationsFragment()
            )
        }

        btnLogin.setOnClickListener {
            authFireBase()
        }

    }

    fun authFireBase() {
        if (etEmail.text.toString().isEmpty() || etPassword.text.toString().isEmpty()) {
            toastLogin("email or password field is empty")
        } else {
            Log.d("Debug", "click btnLogin -> else ->")
            auth.signInWithEmailAndPassword(
                etEmail.text.toString(),
                etPassword.text.toString()
            )
                .addOnCompleteListener(this@LoginFragment as Activity) { task ->
                    Log.d("Debug", "click btnLogin -> else -> task ->")
                    if (task.isSuccessful) {
                        toastLogin("Authentication success")
                        val action = LoginFragmentDirections
                            .actionLoginFragmentToCongratulationsFragment()
                        findNavController().navigate(action)
                    } else {
                        toastLogin("Authentication failed")
                    }
                }
        }
    }

    fun toastLogin(text: String) {
        Toast.makeText(this@LoginFragment as Activity, "$text", Toast.LENGTH_LONG).show()
    }


}